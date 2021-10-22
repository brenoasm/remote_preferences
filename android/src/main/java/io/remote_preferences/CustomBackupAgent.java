package io.remote_preferences;

import android.app.backup.BackupAgentHelper;
import android.app.backup.BackupDataInput;
import android.app.backup.BackupDataOutput;
import android.app.backup.BackupManager;
import android.app.backup.RestoreObserver;
import android.app.backup.SharedPreferencesBackupHelper;
import android.content.Context;
import android.os.ParcelFileDescriptor;
import android.util.Log;

import java.io.IOException;

public class CustomBackupAgent extends BackupAgentHelper {
    static final String REMOTE_PREFERENCES_KEY = "remote_preferences";

    static final String REMOTE_PREFERENCES_BACKUP_KEY = "remote_preferences_backup";

    @Override
    public void onCreate() {
        SharedPreferencesBackupHelper helper =
                new SharedPreferencesBackupHelper(this, REMOTE_PREFERENCES_KEY);

        addHelper(REMOTE_PREFERENCES_BACKUP_KEY, helper);

        Log.d("RemotePreferences", "SharedPreferences backup helper added");
    }

    @Override
    public void onRestore(BackupDataInput data, int appVersionCode, ParcelFileDescriptor newState)
            throws IOException {
        Log.d("RemotePreferences", "Backup restore initialized");

        super.onRestore(data, appVersionCode, newState);
    }

    @Override
    public void onBackup(
            ParcelFileDescriptor oldState, BackupDataOutput data, ParcelFileDescriptor newState)
            throws IOException {
        Log.d("RemotePreferences", "Backup initialized");

        super.onBackup(oldState, data, newState);
    }

    public static void requestBackup(Context context) {
        BackupManager backupManager = new BackupManager(context);

        backupManager.dataChanged();
    }
}
