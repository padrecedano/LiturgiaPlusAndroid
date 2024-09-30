/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.deiverbum.app.core.database.di

import androidx.room.DeleteColumn
import androidx.room.DeleteTable
import androidx.room.RenameColumn
import androidx.room.migration.AutoMigrationSpec
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

/**
 * Automatic schema migrations sometimes require extra instructions to perform the migration, for
 * example, when a column is renamed. These extra instructions are placed here by creating a class
 * using the following naming convention `SchemaXtoY` where X is the schema version you're migrating
 * from and Y is the schema version you're migrating to. The class should implement
 * `AutoMigrationSpec`.
 */
internal object DatabaseMigrations {

    @RenameColumn(
        tableName = "topics",
        fromColumnName = "description",
        toColumnName = "shortDescription",
    )
    class Schema2to3 : AutoMigrationSpec

    @DeleteColumn(
        tableName = "news_resources",
        columnName = "episode_id",
    )
    @DeleteTable.Entries(
        DeleteTable(
            tableName = "episodes_authors",
        ),
        DeleteTable(
            tableName = "episodes",
        ),
    )
    class Schema10to11 : AutoMigrationSpec

    @DeleteTable.Entries(
        DeleteTable(
            tableName = "news_resources_authors",
        ),
        DeleteTable(
            tableName = "authors",
        ),
    )
    class Schema11to12 : AutoMigrationSpec

    // Migration from 2 to 3, Room 2.2.0.
    val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL(
                """
                INSERT  OR IGNORE INTO  news_resources VALUES ('1', 'Android Dev Summit ’22: Coming to you, online and around the world! ⛰️','Android Dev Summit is back, so join us online or in person — for the first time since 2019 — at locations around the world. We’ll be sharing the sessions live on YouTube in three tracks spread across three weeks.','https://android-developers.googleblog.com/2022/10/android-dev-summit.html','https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEh1VWQmqQu6wDswls9f_5NpEQnq4eR57g2NwzWvhKItcKtV6rb_Cyo75XSyL6vvmCIo4tzQn-8taNagEp7QG0KU1L4yMqwbYozNMzBMEFxEN2XintAhy5nLI4RQDaOXr8dgiIFdGOBMdl577Ndelzc0tDBzjI6mz7e4MF8_Tn09KWguZi6I-bS5NbJn/w1200-h630-p-k-no-nu/unnamed%20%2816%29.png','2022-10-04T23:00:00.000Z','Event 📆')
                """.trimIndent()
            )
            database.execSQL(
                """
                INSERT  OR IGNORE INTO  news_resources VALUES ('2', '2Android Dev Summit ’22: Coming to you, online and around the world! ⛰️','Android Dev Summit is back, so join us online or in person — for the first time since 2019 — at locations around the world. We’ll be sharing the sessions live on YouTube in three tracks spread across three weeks.','https://android-developers.googleblog.com/2022/10/android-dev-summit.html','https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEh1VWQmqQu6wDswls9f_5NpEQnq4eR57g2NwzWvhKItcKtV6rb_Cyo75XSyL6vvmCIo4tzQn-8taNagEp7QG0KU1L4yMqwbYozNMzBMEFxEN2XintAhy5nLI4RQDaOXr8dgiIFdGOBMdl577Ndelzc0tDBzjI6mz7e4MF8_Tn09KWguZi6I-bS5NbJn/w1200-h630-p-k-no-nu/unnamed%20%2816%29.png','2022-10-04T23:00:00.000Z','Event 📆')
                """.trimIndent()
            )
            database.execSQL(
                """
                INSERT  OR IGNORE INTO  topics (id,name,shortDescription,longDescription,imageUrl,url) VALUES ('1','Headlines','News youll definitely be interested in','The latest events and announcements from the world of Android development.','https://firebasestorage.googleapis.com/v0/b/now-in-android.appspot.com/o/img%2Fic_topic_Headlines.svg?alt=media&token=506faab0-617a-4668-9e63-4a2fb996603f','')
                """.trimIndent()
            )
            database.execSQL(
                """
                INSERT  OR IGNORE INTO  topics (id,name,shortDescription,longDescription,imageUrl,url) VALUES ('2','Headlines','2News youll definitely be interested in','2The latest events and announcements from the world of Android development.','https://firebasestorage.googleapis.com/v0/b/now-in-android.appspot.com/o/img%2Fic_topic_Headlines.svg?alt=media&token=506faab0-617a-4668-9e63-4a2fb996603f','')
                """.trimIndent()
            )
            database.execSQL(
                """
                INSERT  OR IGNORE INTO  news_resources_topics VALUES (1,1)
                """.trimIndent()
            )
            database.execSQL(
                """
                INSERT  OR IGNORE INTO  news_resources_topics VALUES (1,2)
                """.trimIndent()
            )
        }
    }

}
