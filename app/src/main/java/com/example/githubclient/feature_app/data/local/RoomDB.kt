package com.example.githubclient.feature_app.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.githubclient.feature_app.data.local.dao.DownloadDao
import com.example.githubclient.feature_app.data.local.dao.GithubRepositoryDao
import com.example.githubclient.feature_app.data.local.type_converter.LicenseTypeConverter
import com.example.githubclient.feature_app.data.local.type_converter.OwnerTypeConverter
import com.example.githubclient.feature_app.data.local.type_converter.StringListTypeConverter
import com.example.githubclient.feature_app.domain.entity.download.DownloadEntity
import com.example.githubclient.feature_app.domain.entity.search_history.SearchHistoryEntity

@Database(
    entities = [
        SearchHistoryEntity::class,
        DownloadEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    OwnerTypeConverter::class,
    LicenseTypeConverter::class,
    StringListTypeConverter::class,
)
abstract class RoomDB : RoomDatabase() {

    abstract fun getGithubRepositoryDao(): GithubRepositoryDao
    abstract fun getDownloadDao(): DownloadDao

    companion object {
        @Volatile
        private var INSTANCE: RoomDB? = null
        fun getInstance(context: Context): RoomDB {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        RoomDB::class.java,
                        "github_client_db"
                    ).fallbackToDestructiveMigration()
                        .build()
                }
                return instance
            }

        }
    }

}