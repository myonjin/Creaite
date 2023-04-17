package com.kgh.prototype

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.kgh.prototype.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
//    private lateinit var viewModel1: PictureViewModel
//    private val mainViewModel: MainViewModel by viewModels {
//        MainViewModelFactory((application as SignEzApplication).container)
//    }
    private val REQUEST_CODE_SOMETHING = 1
    private val REQUEST_CODE_PERMISSIONS = 2
    val permissions = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE
    )
    lateinit var navController: NavHostController

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_CODE_SOMETHING -> {
                    Log.d("Testing","testing - request code")
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
//        viewModel1 = ViewModelProvider( // 분석 이미지
//            this,
//            factory = AppViewModelProvider.Factory
//        )[PictureViewModel::class.java]

        setContent {
            navController = rememberNavController()


            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                HexaApp(
                    activity = this,
                    navController = navController
                )
            }
        }
        requestPermissions()
    }

        @RequiresApi(Build.VERSION_CODES.M)
        private fun requestPermissions() {
        val permissionsToRequest = mutableListOf<String>()
        for (permission in permissions) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    permission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                permissionsToRequest.add(permission)
                shouldShowRequestPermissionRationale(permission)
            }
        }

        if (permissionsToRequest.isNotEmpty()) { // 권한 요청할게 있으면 요청 날림.
            ActivityCompat.requestPermissions(
                this,
                permissionsToRequest.toTypedArray(),
                REQUEST_CODE_PERMISSIONS
            )
        } else {
            Log.d("permissionis","granted")
        }
    }

}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        Greeting("Android")
    }
}