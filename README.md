# 書籍管理システム

## 概要

※このプロジェクトはコーディング課題の一環として作成されました。

Kotlin、Spring Boot、jOOQを使用して実装された書籍管理システムです。図書館システムにおける書籍と著者を管理するためのRESTful APIを提供します。

## 機能

- 書籍と著者のCRUD操作
- 著者別の書籍取得
- 入力バリデーション
- エラーハンドリング
- Flywayを使用したデータベースマイグレーション
- TestContainersを使用した統合テスト

## 使用技術

- Kotlin
- Spring Boot
- jOOQ
- PostgreSQL
- データベースマイグレーション用のFlyway
- テスト用のJUnit 5
- 統合テスト用のTestContainers

## プロジェクト構造

```
src
├── main
│   ├── kotlin
│   │   └── com
│   │       └── example
│   │           └── bookmanagement
│   │               ├── controller
│   │               ├── entity
│   │               ├── exception
│   │               ├── repository
│   │               │   └── impl
│   │               └── service
│   └── resources
│       └── db
│           └── migration
└── test
    ├── kotlin
    │   └── com
    │       └── example
    │           └── bookmanagement
    │               ├── controller
    │               ├── repository
    │               └── service
    └── resources
```

## セットアップと実行

### 前提条件

- JDK 21
- Docker（PostgreSQL実行用）
- Gradle

### 手順

1. リポジトリをクローンする：
   ```shell
   git clone <リポジトリURL>
   cd book-management
   ```

2. Docker Composeを使用してPostgreSQLデータベースを起動する：
   ```shell
   docker-compose up -d
   ```

3. アプリケーションを実行する：
   ```shell
   ./gradlew bootRun
   ```

アプリケーションは `http://localhost:8080` で起動します。

## APIエンドポイント

### 著者
- GET `/api/authors` - 全著者を取得
- GET `/api/authors/{id}` - IDで著者を取得
- POST `/api/authors` - 新しい著者を作成
- PUT `/api/authors/{id}` - 著者を更新

### 書籍
- GET `/api/books` - 全書籍を取得
- GET `/api/books/{id}` - IDで書籍を取得
- GET `/api/books/author/{authorId}` - 著者IDで書籍を取得
- POST `/api/books` - 新しい書籍を作成
- PUT `/api/books/{id}` - 書籍を更新

## テスト

テストを実行するには、次のコマンドを使用します：
```shell
./gradlew test
```

## データベースマイグレーション

データベースマイグレーションはFlywayで管理されています。マイグレーションスクリプトは `src/main/resources/db/migration` にあります。

マイグレーションを手動で実行するには：
```shell
./gradlew flywayMigrate
```

