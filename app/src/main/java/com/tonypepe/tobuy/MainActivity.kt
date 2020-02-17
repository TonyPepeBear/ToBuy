package com.tonypepe.tobuy

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.navigation.findNavController
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    val viewModel: MainViewModel by viewModels()
    lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        // navigation
        toggle = ActionBarDrawerToggle(this, drawer, toolbar, R.string.ok, R.string.cancel)
        navigation.setNavigationItemSelectedListener(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        logd("onNavigationItemSelected ${item.title}")
        val controller = findNavController(R.id.content_fragment)
        when (item.itemId) {
            R.id.action_list -> {
                controller.navigate(R.id.mainFragment)
            }
            R.id.action_settings -> {
                controller.navigate(R.id.settingsFragment)
            }
        }
        drawer.closeDrawer(GravityCompat.START)
        return true
    }
}
