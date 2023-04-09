# 株式会社ゆめみ Android エンジニアコードチェック課題

## 概要

本プロジェクトは株式会社ゆめみ（以下弊社）が、弊社に Android エンジニアを希望する方に出す課題のベースプロジェクトです。本課題が与えられた方は、下記の概要を詳しく読んだ上で課題を取り組んでください。

## アプリ仕様

本アプリは GitHub のリポジトリを検索するアプリです。

<img src="docs/app.gif" width="320">

### 環境

- IDE：Android Studio Electric Eel | 2022.1.1 Patch 1
- Kotlin：1.5.31
- Java：11
- Gradle：7.0.1
- minSdk：23
- targetSdk：31

※ ライブラリの利用はオープンソースのものに限ります。

### 動作

1. 何かしらのキーワードを入力
2. GitHub API（`search/repositories`）でリポジトリを検索し、結果一覧を概要（リポジトリ名）で表示
3. 特定の結果を選択したら、該当リポジトリの詳細（リポジトリ名、オーナーアイコン、プロジェクト言語、Star 数、Watcher 数、Fork 数、Issue 数）を表示

## 課題取り組み方法

Issues を確認した上、本プロジェクトを [**Duplicate** してください](https://help.github.com/en/github/creating-cloning-and-archiving-repositories/duplicating-a-repository)（Fork しないようにしてください。必要ならプライベートリポジトリにしても大丈夫です）。今後のコミットは全てご自身のリポジトリで行ってください。

コードチェックの課題 Issue は全て [`課題`](https://github.com/yumemi-inc/android-engineer-codecheck/milestone/1) Milestone がついており、難易度に応じて Label が [`初級`](https://github.com/yumemi-inc/android-engineer-codecheck/issues?q=is%3Aopen+is%3Aissue+label%3A初級+milestone%3A課題)、[`中級`](https://github.com/yumemi-inc/android-engineer-codecheck/issues?q=is%3Aopen+is%3Aissue+label%3A中級+milestone%3A課題+) と [`ボーナス`](https://github.com/yumemi-inc/android-engineer-codecheck/issues?q=is%3Aopen+is%3Aissue+label%3Aボーナス+milestone%3A課題+) に分けられています。課題の必須／選択は下記の表とします。

## コミットのメッセージ
コミットメッセージには最初にprefixを書いています。以下はprefix一覧です。
* add: 新規ファイル追加
* remove：ファイル削除
* update：機能修正
* feat: 新しい機能
* fix: バグの修正
* docs: ドキュメントのみの変更
* style: 空白、フォーマット、セミコロン追加など
* refactor: 仕様に影響がないコード改善(リファクタ)
* perf: パフォーマンス向上関連
* test: テスト関連
* chore: ビルド、補助ツール、ライブラリ関連
