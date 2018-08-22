package ru.spcm.apps.mtgpro.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import android.os.Environment
import ru.spcm.apps.mtgpro.di.modules.DbModule
import ru.spcm.apps.mtgpro.tools.AppExecutors
import ru.spcm.apps.mtgpro.tools.Logger
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import javax.inject.Inject

class BackupRepo@Inject
constructor(private val appExecutors: AppExecutors) {
    private val path = Environment.getExternalStorageDirectory().path + "/data/"

    fun backup(context: Context): LiveData<Boolean> {
        val result = MutableLiveData<Boolean>()
        appExecutors.diskIO().execute{
            var success = false
            val fullBackupDir = path + context.packageName
            val dbFile = context.getDatabasePath(DbModule.DB_NAME)
            if (dbFile.exists()) {
                try {
                    val dir = File(fullBackupDir)
                    if (!dir.exists() && !dir.mkdirs()) {
                        Logger.e("Каталоги не созданы")
                    }

                    val backup = File(fullBackupDir, DbModule.DB_NAME)
                    if (!backup.exists() && !backup.createNewFile()) {
                        Logger.e("Копия не создана")
                    }
                    FileInputStream(dbFile).use { fileInputStream ->
                        val src = fileInputStream.channel
                        FileOutputStream(backup).use { fileOutputStream ->
                            val dst = fileOutputStream.channel
                            dst.transferFrom(src, 0, src.size())
                            src.close()
                            dst.close()
                        }
                    }

                    val walFile = context.getDatabasePath(DbModule.DB_NAME).absolutePath + "-wal"
                    val wal = File(fullBackupDir, DbModule.DB_NAME + "-wal")
                    if (!wal.exists() && !wal.createNewFile()) {
                        Logger.e("Журнал не создан")
                    }
                    FileInputStream(walFile).use { fileInputStream ->
                        val src = fileInputStream.channel
                        FileOutputStream(wal).use { fileOutputStream ->
                            val dst = fileOutputStream.channel
                            dst.transferFrom(src, 0, src.size())
                            src.close()
                            dst.close()
                        }
                    }

                    val shmFile = context.getDatabasePath(DbModule.DB_NAME).absolutePath + "-shm"
                    val shm = File(fullBackupDir, DbModule.DB_NAME + "-shm")
                    if (!shm.exists() && !shm.createNewFile()) {
                        Logger.e("Журнал не создан")
                    }
                    FileInputStream(shmFile).use { fileInputStream ->
                        val src = fileInputStream.channel
                        FileOutputStream(shm).use { fileOutputStream ->
                            val dst = fileOutputStream.channel
                            dst.transferFrom(src, 0, src.size())
                            src.close()
                            dst.close()
                            success = true

                        }
                    }
                } catch (ex: IOException) {
                    Logger.e(ex)
                }

            }
            appExecutors.mainThread().execute{
                result.postValue(success)
            }
        }

        return result
    }

    fun restore(context: Context): LiveData<Boolean>  {
        val result = MutableLiveData<Boolean>()
        appExecutors.diskIO().execute {
            var success = false
            val fullBackupDir = path + context.packageName
            val backup = File(fullBackupDir, DbModule.DB_NAME)
            val wal = File(fullBackupDir, DbModule.DB_NAME + "-wal")
            val shm = File(fullBackupDir, DbModule.DB_NAME + "-shm")
            if (backup.exists() && wal.exists()) {
                try {
                    val dbFile = context.getDatabasePath(DbModule.DB_NAME)
                    if (!dbFile.exists() && !dbFile.createNewFile()) {
                        Logger.e("Локальная БД не создана")
                    }
                    FileInputStream(backup).use { fileInputStream ->
                        val src = fileInputStream.channel
                        FileOutputStream(dbFile).use { fileOutputStream ->
                            val dst = fileOutputStream.channel
                            dst.transferFrom(src, 0, src.size())
                            src.close()
                            dst.close()
                        }
                    }

                    val walFile = File(context.getDatabasePath(DbModule.DB_NAME).absolutePath + "-wal")
                    if (!walFile.exists() && !walFile.createNewFile()) {
                        Logger.e("Локальный журнал не создан")
                    }
                    FileInputStream(wal).use { fileInputStream ->
                        val src = fileInputStream.channel
                        FileOutputStream(walFile).use { fileOutputStream ->
                            val dst = fileOutputStream.channel
                            dst.transferFrom(src, 0, src.size())
                            src.close()
                            dst.close()
                        }
                    }

                    val shmFile = File(context.getDatabasePath(DbModule.DB_NAME).absolutePath + "-shm")
                    if (!shmFile.exists() && !shmFile.createNewFile()) {
                        Logger.e("Локальный журнал не создан")
                    }
                    FileInputStream(shm).use { fileInputStream ->
                        val src = fileInputStream.channel
                        FileOutputStream(shmFile).use { fileOutputStream ->
                            val dst = fileOutputStream.channel
                            dst.transferFrom(src, 0, src.size())
                            src.close()
                            dst.close()
                            success = true
                        }
                    }
                } catch (ex: IOException) {
                    Logger.e(ex)
                }

            }
            appExecutors.mainThread().execute{
                result.postValue(success)
            }
        }
        return result
    }
}