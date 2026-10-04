# Phase 0: Thymeleaf / jQuery 開発環境

## 現在の構成

- Spring Boot 4.0.4、Java toolchain 21、Gradle Wrapper 9.4.0。
- `spring-boot-starter-thymeleaf` は導入済み。
- `DailyLogController` の `@Controller` が Model とビュー名を返す。
  - 一覧・既存の期間検索: `daily-log/list`
  - 登録: `daily-log/new`
  - 編集: `daily-log/edit`
  - 登録・更新・削除後: `/daily-log` へリダイレクト
- HTML は `src/main/resources/templates/daily-log/` に配置済み。
- Thymeleaf の `th:each`、`th:text`、`th:field`、`th:action` などを使用済み。
- `spring.thymeleaf.cache=false` が設定済み。
- Phase 0 開始時、`resources/static` は空で、外部 JavaScript ファイルと jQuery は未導入。
  削除確認には既存のインライン `confirm()` を使用。
- プロジェクト内に `gradle.properties` はない。

## 最小変更

既存の Thymeleaf starter と Spring Boot の自動設定をそのまま使用する。
登録・編集 HTML に `xmlns:th` を明記し、3画面の head に次を追加した。

```html
<script th:src="@{/js/vendor/jquery-4.0.0.min.js}"></script>
```

jQuery 4.0.0 の通常版（slim ではない）を公式配布元から取得し、
`src/main/resources/static/js/vendor/jquery-4.0.0.min.js` に保存した。
Spring Boot が `/js/vendor/jquery-4.0.0.min.js` として配信する。
Thymeleaf の URL 式によりコンテキストパスにも対応する。
画面表示時に外部 CDN 接続は不要。ファイル先頭の著作権・ライセンス表記を保持する。

- 配布元: https://code.jquery.com/jquery-4.0.0.min.js
- 公式リリース: https://blog.jquery.com/2026/01/17/jquery-4-0-0/
- ライセンス: https://jquery.com/license/

後続の画面固有 JavaScript は `resources/static/js/` に置き、jQuery の script より後で読み込む。
今回はアプリ固有のイベント処理や Ajax、検索機能は追加していない。
ブラウザーの Console で `jQuery.fn.jquery` が `4.0.0` になることで読み込みを確認できる。

## 起動方法

ローカル PostgreSQL と既存の `application.properties` の接続設定を使用する。
Java 21 を `JAVA_HOME` に設定してから実行する。

Eclipse の既存構成:

1. 実行構成 → Gradle タスク → `weight-management-app - bootRun` を実行。
2. Console に `Listening for transport dt_socket at address: 5005` が出たら、
   デバッグ構成 → リモート Java アプリケーション → `新規構成` を選択し、
   `localhost:5005` にデバッグ接続。
3. Spring Boot の起動完了ログを確認し、`http://localhost:8080/daily-log` を開く。

`build.gradle` の `bootRun.debugOptions` は `enabled=true`、`port=5005`、
`server=true`、`suspend=true` の既存設定を保持している。
デバッガー接続前の待機はこの設定によるもの。

デバッグ接続なしで別ポートに起動する場合:

```powershell
.\gradlew.bat bootJar
java -jar build\libs\weight-management-app-0.0.1-SNAPSHOT.jar --server.port=18080
```

## 回帰確認

```powershell
.\gradlew.bat test
```

`DailyLogWebIntegrationTests` は実際の Spring Context、Thymeleaf、PostgreSQL を使い、
一覧・登録・既存期間検索・編集・削除・必須値未入力時のリダイレクトと静的 JS 配信を確認する。
既存レコードと重ならない日付を選び、テスト内の DB 書き込みはトランザクションでロールバックする。
Flyway/JPA の既存起動処理も動くため、実行には既存のローカル DB が必要。

2026-10-03 の確認結果:

- `test bootJar --offline --console=plain`: 成功。
- 回帰テスト追加後の `test --offline --console=plain`: 4件成功、失敗・エラー0件。
- Java 21.0.9、ポート18080で JAR を起動し、Tomcat 起動と
  `Started WeightManagementAppApplication` を確認。
- PostgreSQL への接続、Flyway の既存2件の検証、スキーマ002が最新であることを確認。
  新たなマイグレーションは実行されなかった。
- ブラウザーで一覧・登録・既存データの編集画面を表示できることを確認。
  3画面ともローカル jQuery の script URL を確認し、収集されたブラウザーログにエラーなし。
- jQuery ファイルの HTTP 配信と内容は回帰テストで確認。
  ブラウザー検証APIの読み取り専用スコープではページのグローバル変数を参照できないため、
  `jQuery.fn.jquery` の実ブラウザー内の値の直接確認は未実施。
- Controller / Service / Mapper / SQL / build.gradle は変更していない。

## 既存の注意点（Phase 0 では変更しない）

- 期間検索 Controller の `startDate` / `endDate` には日付ではなく `dailyLogs` が設定されている。
  検索結果の表示と、検索後の日付入力値の保持は別の確認項目になる。
- Eclipse の Gradle 引数 `-Porg.gradle.java.installations.paths=...` について、
  Console に非推奨警告が出ている。起動構成の変更は今回行わない。
