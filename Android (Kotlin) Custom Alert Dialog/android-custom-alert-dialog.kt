
    // custom dialog programmatically
    fun showMaterialAlertDialog(
        customAlert: CustomAlert,
        positiveClick: () -> Unit = {},
        negativeClick: () -> Unit = {}
    ) {
        val dialog = Dialog(this, R.style.AlertDialogSlideAnim)
        dialog.setContentView(R.layout.custom_alert_dialog)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.setGravity(Gravity.BOTTOM)
        val titleAlertText = dialog.findViewById<TextView>(R.id.tvTitleAlert)
        titleAlertText.text = customAlert.title
        val msgAlertText = dialog.findViewById<TextView>(R.id.tvMsgAlert)
        msgAlertText.text = customAlert.message
        val alertIcon = dialog.findViewById<ImageView>(R.id.ivIconAlert)
        alertIcon.setImageResource(customAlert.icon)
        val positiveButton = dialog.findViewById<Button>(R.id.btnPositiveAlert)
        positiveButton.text = customAlert.btnPositiveText
        positiveButton.setOnClickListener {
            positiveClick()
            dialog.dismiss()
        }
        val negativeButton = dialog.findViewById<Button>(R.id.btnNegativeAlert)
        if (customAlert.btnNegative!!) negativeButton.show(true)
        negativeButton.text = customAlert.btnNegativeText
        negativeButton.setOnClickListener{
            negativeClick()
            dialog.dismiss()
        }
        dialog.show()
    }

    // custom dialog layout (R.layout.custom_alert_dialog)
    <?xml version="1.0" encoding="utf-8"?>
    <androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:app="http://schemas.android.com/apk/res-auto"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:background="@drawable/alert_dialog_background"
        android:backgroundTint="@android:color/white"
        android:layout_margin="16dp
        android:padding="16dp"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent">

        <androidx.appcompat.widget.AppCompatImageView
        android:id="@+id/ivIconAlert"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:adjustViewBounds="true"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintWidth_percent="0.1" />

        <androidx.appcompat.widget.AppCompatTextView
        android:id="@+id/tvTitleAlert"
        style="?textAppearanceBody2"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginTop="16dp"
        android:textSize="16sp"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/ivIconAlert" />

        <androidx.appcompat.widget.AppCompatTextView
        android:id="@+id/tvMsgAlert"
        style="?textAppearanceBody3"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:layout_marginStart="16dp"
        android:layout_marginTop="16dp"
        android:layout_marginEnd="16dp"
        android:gravity="center"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/tvTitleAlert" />

        <androidx.appcompat.widget.LinearLayoutCompat
            android:id="@+id/llcButtonsAlert"
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_marginTop="16dp"
            android:orientation="horizontal"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toBottomOf="@+id/tvMsgAlert">

            <com.google.android.material.button.MaterialButton
            android:id="@+id/btnPositiveAlert"
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_marginEnd="4dpr"
            android:layout_weight="1"
            android:paddingTop="4dp"
            android:paddingBottom="4dp"
            app:cornerRadius="64dp" />

            <com.google.android.material.button.MaterialButton
            android:id="@+id/btnNegativeAlert"
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_marginStart="4dp"
            android:layout_marginEnd="4dp"
            android:layout_weight="1"
            android:paddingTop="4dp"
            android:paddingBottom="4dp"
            android:visibility="gone"
            app:cornerRadius="64dp" />

        </androidx.appcompat.widget.LinearLayoutCompat>
    </androidx.constraintlayout.widget.ConstraintLayout>

    // Slide Up (R.anim.slide_up)
    <?xml version="1.0" encoding="utf-8"?>
    <translate xmlns:android="http://schemas.android.com/apk/res/android"
        android:fromYDelta="50%p"
        android:toYDelta="0%p"
        android:duration="@android:integer/config_longAnimTime"/>

    // Slide Down (R.anim.slide_down)
    <?xml version="1.0" encoding="utf-8"?>
    <translate xmlns:android="http://schemas.android.com/apk/res/android"
        android:duration="@android:integer/config_longAnimTime"
        android:fromYDelta="0%p"
        android:toYDelta="50%p" />

    // Background (R.drawable.alert_dialog_background)
    <?xml version="1.0" encoding="utf-8"?>
    <shape android:shape="rectangle" xmlns:android="http://schemas.android.com/apk/res/android">
        <corners android:radius="@dimen/size_default" />
        <stroke android:width="@dimen/size_1dp" android:color="@color/colorDarkGray" />
    </shape>

    // Style (R.style.AlertDialogSlideAnim)
    <style name="AlertDialogSlideAnim" parent="@android:style/Theme.Dialog">
        <item name="android:windowEnterAnimation">@anim/slide_up</item>
        <item name="android:windowExitAnimation">@anim/slide_down</item>
        <item name="android:windowMinWidthMajor">100%</item>
        <item name="android:windowMinWidthMinor">100%</item>
    </style>


    // Model - Data Class
    data class CustomAlert(
        var icon: Int,
        var title: String,
        var message: String,
        var btnPositiveText: String,
        var btnNegative: Boolean? = false,
        var btnNegativeText: String
    )