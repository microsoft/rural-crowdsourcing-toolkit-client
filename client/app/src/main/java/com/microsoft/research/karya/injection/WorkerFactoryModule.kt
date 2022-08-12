package com.microsoft.research.karya.injection

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.microsoft.research.karya.data.manager.AuthManager
import com.microsoft.research.karya.data.manager.SyncDelegatingWorkerFactory
import com.microsoft.research.karya.data.repo.*
import com.microsoft.research.karya.injection.qualifier.FilesDir
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class WorkerFactoryModule {

  @Provides
  @Singleton
  fun providesNgWorkerFactory(
    assignmentRepository: AssignmentRepository,
    karyaFileRepository: KaryaFileRepository,
    microTaskRepository: MicroTaskRepository,
    paymentRepository: PaymentRepository,
    workerRepository: WorkerRepository,
    datastore: DataStore<Preferences>,
    @FilesDir fileDirPath: String,
    authManager: AuthManager,
  ): SyncDelegatingWorkerFactory {
    return SyncDelegatingWorkerFactory(
      assignmentRepository,
      karyaFileRepository,
      microTaskRepository,
      paymentRepository,
      workerRepository,
      datastore,
      fileDirPath,
      authManager
    )
  }
}
