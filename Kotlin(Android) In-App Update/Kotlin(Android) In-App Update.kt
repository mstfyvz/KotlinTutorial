#Android (Kotlin) In-App Update

#strings.xml

    <!-- Actions-->
    <string name="action_grant">Grant</string>
    <string name="action_retry">Retry</string>
    <string name="action_update">Update</string>
    <string name="action_restart">Restart</string>

    <!-- Information -->
    <string name="info_processing">Processing</string>
    <string name="info_pending">Pending</string>
    <string name="info_restart">Restart</string>
    <string name="info_failed">Failed</string>
    <string name="info_canceled">Canceled</string>
    <string name="info_downloading">Downloading</string>
    <string name="info_downloaded">Downloaded</string>
    <string name="info_installing">Installing</string>
    <string name="info_installed">Installed</string>

    <!-- Dialog -->
    <string name="update_title">App update</string>
    <string name="update_message">Application successfully updated! You need to restart the app in
    order to use this new features.</string>

    <!-- Toast -->
    <string name="toast_started">App update started</string>
    <string name="toast_updated">App successfully updated</string>
    <string name="toast_failed">It was not possible to update your app</string>
    <string name="toast_cancelled">App update cancelled</string>


# KOTLIN

	class MainActivity : BaseActivity() {

    private lateinit var updateListener: InstallStateUpdatedListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Fabric.with(this, Crashlytics())

        checkForUpdates()

    }

    private fun checkForUpdates() {
        //1
        //val appUpdateManager = AppUpdateManagerFactory.create(baseContext)
        val appUpdateManager : AppUpdateManager
        if (BuildConfig.DEBUG) {
            appUpdateManager = FakeAppUpdateManager(baseContext)
            appUpdateManager.setUpdateAvailable(2)
        } else {
            appUpdateManager = AppUpdateManagerFactory.create(baseContext)
        }
        val appUpdateInfo = appUpdateManager.appUpdateInfo
        appUpdateInfo.addOnSuccessListener {
            //2
            handleUpdate(appUpdateManager, appUpdateInfo)
        }
    }

    private fun handleUpdate(manager: AppUpdateManager, info: Task<AppUpdateInfo>) {
        if (APP_UPDATE_TYPE_SUPPORTED == AppUpdateType.IMMEDIATE) {
            handleImmediateUpdate(manager, info)
        } else if (APP_UPDATE_TYPE_SUPPORTED == AppUpdateType.FLEXIBLE) {
            handleFlexibleUpdate(manager, info)
        }
    }

    private fun handleImmediateUpdate(manager: AppUpdateManager, info: Task<AppUpdateInfo>) {
        //1
        if ((info.result.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE ||
                    //2
                    info.result.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) &&
                    //3
                    info.result.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
            //4
            manager.startUpdateFlowForResult(info.result, AppUpdateType.IMMEDIATE, this, REQUEST_UPDATE)
        }

        if (BuildConfig.DEBUG) {
            val fakeAppUpdate = manager as FakeAppUpdateManager
            if (fakeAppUpdate.isImmediateFlowVisible) {
                fakeAppUpdate.userAcceptsUpdate()
                fakeAppUpdate.downloadStarts()
                fakeAppUpdate.downloadCompletes()
                launchRestartDialog(manager)
            }
        }
    }

    private fun handleFlexibleUpdate(manager: AppUpdateManager, info: Task<AppUpdateInfo>) {
        if ((info.result.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE ||
                    info.result.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) &&
            info.result.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)) {
            btn_update.visibility = View.VISIBLE
            setUpdateAction(manager, info)
        }
    }

    private fun setUpdateAction(manager: AppUpdateManager, info: Task<AppUpdateInfo>) {
        //1
        btn_update.setOnClickListener {
            //2
            updateListener = InstallStateUpdatedListener {
                //3
                btn_update.visibility = View.GONE
                tv_status.visibility = View.VISIBLE
                //4
                when (it.installStatus()) {
                    InstallStatus.FAILED, InstallStatus.UNKNOWN -> {
                        tv_status.text = getString(R.string.info_failed)
                        btn_update.visibility = View.VISIBLE
                    }
                    InstallStatus.PENDING -> {
                        tv_status.text = getString(R.string.info_pending)
                    }
                    InstallStatus.CANCELED -> {
                        tv_status.text = getString(R.string.info_canceled)
                    }
                    InstallStatus.DOWNLOADING -> {
                        tv_status.text = getString(R.string.info_downloading)
                    }
                    //5
                    InstallStatus.DOWNLOADED -> {
                        tv_status.text = getString(R.string.info_downloaded)
                        launchRestartDialog(manager)
                    }
                    InstallStatus.INSTALLING -> {
                        tv_status.text = getString(R.string.info_installing)
                    }
                    //6
                    InstallStatus.INSTALLED -> {
                        tv_status.text = getString(R.string.info_installed)
                        manager.unregisterListener(updateListener)
                    }
                    else -> {
                        tv_status.text = getString(R.string.info_restart)
                    }
                }
            }
            //7
            manager.registerListener(updateListener)
            //8
            manager.startUpdateFlowForResult(info.result, AppUpdateType.FLEXIBLE, this, REQUEST_UPDATE)
        }
    }

    private fun launchRestartDialog(manager: AppUpdateManager) {
        AlertDialog.Builder(this)
            .setTitle("Update Title")
            .setMessage("Update Message")
            .setPositiveButton("Güncelle") { _, _ ->
                manager.completeUpdate()
            }
            .create().show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        //1
        if (REQUEST_UPDATE == requestCode) {
            when (resultCode) {
                //2
                Activity.RESULT_OK -> {
                    if (APP_UPDATE_TYPE_SUPPORTED == AppUpdateType.IMMEDIATE) {
                        Toast.makeText(baseContext, R.string.toast_updated, Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(baseContext, R.string.toast_started, Toast.LENGTH_SHORT).show()
                    }
                }
                //3
                Activity.RESULT_CANCELED -> {
                    Toast.makeText(baseContext, R.string.toast_cancelled, Toast.LENGTH_SHORT).show()
                }
                //4
                ActivityResult.RESULT_IN_APP_UPDATE_FAILED -> {
                    Toast.makeText(baseContext, R.string.toast_failed, Toast.LENGTH_SHORT).show()
                }
            }
            super.onActivityResult(requestCode, resultCode, data)
        }
    }


    companion object {
        private const val REQUEST_UPDATE = 100
        private const val APP_UPDATE_TYPE_SUPPORTED = AppUpdateType.IMMEDIATE
    }

}