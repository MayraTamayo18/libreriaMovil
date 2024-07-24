package com.example.crudlibreria

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(),
NavigationView.OnNavigationItemSelectedListener
{

    /*
    para declarar una variable en kotlin primero se nombre de la variable
    y despues es el tipo de dato
    private lateinit var drawerLayout: DrawerLayout
    en java primero es el tipo de dato y despues el nombre de la variable
    DrawerLayout: drawerLayout
     */

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var buttonDrawerToggle: ImageButton
    private lateinit var navigationView: NavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.drawerLayout)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        drawerLayout = findViewById<DrawerLayout>(R.id.drawerLayout)
        buttonDrawerToggle = findViewById<ImageButton>(R.id.buttonDrawerToggle)
        navigationView = findViewById<NavigationView>(R.id.navigationView)
        navigationView.setNavigationItemSelectedListener (this)

        buttonDrawerToggle.setOnClickListener {
            drawerLayout?.open()
        }


        /*buttonDrawerToggle.setOnClickListener(View.OnClickListener() {
            @Override
            public void onClick(View v){
                drawerLayout.open();
            }
        });*/

        /*navigationView.setNavigationItemSelectedListener(new NavigationView.onNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item){
                int itemId= item.getItemId();
                if(itemId == R.id.navMenu){
                    Toast.makeText(MainActivity.this, "Menu Clicked", Toast.LENGTH_SHORT).show()
                }
                if(itemId == R.id.navLibro){
                    Toast.makeText(MainActivity.this, "libro Clicked", Toast.LENGTH_SHORT).show()
                }
                if(itemId == R.id.navUsuario){
                    Toast.makeText(MainActivity.this, "usuario Clicked", Toast.LENGTH_SHORT).show()
                }
                if(itemId == R.id.navPrestamo){
                    Toast.makeText(MainActivity.this, "prestamo Clicked", Toast.LENGTH_SHORT).show()
                }
                if(itemId == R.id.navMulta){
                    Toast.makeText(MainActivity.this, "multa Clicked", Toast.LENGTH_SHORT).show()
                }
                drawerLayout.close();
                return false;
            }
        });*/


    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            //un caso por cada item
            R.id.navMenu-> {
                Toast.makeText(applicationContext,"menu cliclk",Toast.LENGTH_LONG).show()
                /*
                val manager = supportFragmentManager
                var transaction = manager.beginTransaction()
                transaction.replace(
                    R.id.fragment_layout_main,
                    secondFragment()
                ).commit()
                transaction.addToBackStack(null)
*/
            }

        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true

    }

}