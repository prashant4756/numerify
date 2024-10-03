package com.numerify

import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.numerify.databinding.ActivityMainBinding
import com.numerify.ui.MainActivityViewModel


class MainActivity : AppCompatActivity() {
    private var fab: FloatingActionButton? = null
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var mainActivityViewModel: MainActivityViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater);
        setContentView(binding.root)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setUpViewModel()
        setSupportActionBar(toolbar)
        setUpFloatingActionButton()
        setDrawerLayout()
        setAppVersion()
    }

    private fun setAppVersion() {
        try {
            val pInfo: PackageInfo = getPackageManager().getPackageInfo(getPackageName(), 0)
            val version = pInfo.versionName
            findViewById<AppCompatTextView>(R.id.app_details_text).text = "V $version"
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
    }

    private fun setUpViewModel() {
        mainActivityViewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        mainActivityViewModel.editConfVisibileLiveData.observe(this, Observer {
            if (it) {
                fab?.visibility = VISIBLE
            } else {
                fab?.visibility = GONE
            }
        })
    }

    private fun setDrawerLayout() {
        /*        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)*/
    }

    private fun setUpFloatingActionButton() {
        fab = findViewById(R.id.fab)
        fab?.setOnClickListener { view ->
            setUpEditConfigurationNavigation()
        }
    }

    private fun setUpEditConfigurationNavigation() {
        val navController = findNavController(R.id.nav_host_fragment)
        navController.navigate(R.id.nav_edit_configuration)
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_reset_default -> {
                mainActivityViewModel.resetPrefWithDefault()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}