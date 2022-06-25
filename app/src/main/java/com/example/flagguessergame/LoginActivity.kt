package com.example.flagguessergame

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast

class LoginActivity : AppCompatActivity() {

    val userMgmt = UserCRUD()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    //Metodo para volver al main
    fun goToMainActivity(view: View) {
        var intentMain: Intent = Intent(this, MainActivity::class.java)
        finish()
    }

    //Metodo para borrar la base de datos ingresando como 'adm' y contraseña 'erase'
    @SuppressLint("CutPasteId")
    fun eraseDatabase() {
        val name = findViewById<EditText>(R.id.logname).text.toString()
        val pass = findViewById<EditText>(R.id.logpass).text.toString()

        if (name == "adm") {
            if(pass == "erase") {
                userMgmt.eraseAll(this)
                var intentMain: Intent = Intent(this, MainActivity::class.java)
                finish()
            }
            else
            {
                findViewById<EditText>(R.id.logpass).setBackgroundColor(Color.RED)
                findViewById<EditText>(R.id.logpass).hint = "!!!!!!!!!"
                Toast.makeText(this, "Esta tratando de borrar la base de datos", Toast.LENGTH_LONG).show()
            }
        }
    }

    //Metodo no usado para borrar un usuario
    fun eraseUser() {
        if(!checkRequireds()) {
            userMgmt.eraseByName(this, findViewById<EditText>(R.id.logname).text.toString(), findViewById<EditText>(R.id.logpass).text.toString())
        }
    }

    //Llama al servicio para ver si el usuario existe
    fun checkIfUserExists(): Boolean {
        return userMgmt.exists(this, findViewById<EditText>(R.id.logname).text.toString())
    }

    //Llama al service para valudar si la contraseña ingresada coincide con la que deberia del usuario ingresado
    fun checkIfPasswordMatches(): Boolean {
        return userMgmt.userMatchesPass(this, findViewById<EditText>(R.id.logname).text.toString(), findViewById<EditText>(R.id.logpass).text.toString())
    }

    //Metodo para loggearse (si ya existe) o registrarse (si no existe)
    fun loginOrRegister(view: View){
        //Chequea si el nombre existe:
        if(findViewById<EditText>(R.id.logname).text.toString() == "adm") {
            this.eraseDatabase()
        }
        else
        {
        if(findViewById<EditText>(R.id.logname).text.isEmpty()) {
            var edittxt = findViewById<EditText>(R.id.logname)
            edittxt.setBackgroundColor(Color.parseColor("#FF603D"))
        } else if (findViewById<EditText>(R.id.logpass).text.isEmpty()){
            var edittxt = findViewById<EditText>(R.id.logpass)
            edittxt.setBackgroundColor(Color.parseColor("#FF603D"))
        } else {
        if(checkIfUserExists()) {
            var edittxt = findViewById<EditText>(R.id.logname)
            edittxt.setBackgroundColor(Color.parseColor("#8DFF3D"))
            //Si existe un usuario existente con ese nombre, chequea la pass ingresada:
            if (checkIfPasswordMatches()) {
                //Si la contraseña es la correcta para ese usuario, lo loggea.
                //seteo los valores en los current global datas
                this.redirectToMainLogged(findViewById<EditText>(R.id.logname).text.toString())
                Toast.makeText(this, "Bienvenido... !", Toast.LENGTH_LONG).show()
            }
            else {
                //Si la contraseña no es la correcta para ese usuario, devuelve un toast:
                var edittxt = findViewById<EditText>(R.id.logpass)
                edittxt.setBackgroundColor(Color.parseColor("#FF603D"))
                edittxt.hint = "Prueba otra vez"
                Toast.makeText(this, "Intenta otra vez!", Toast.LENGTH_LONG).show()
            }
        } else {
            //Si el usuario ingresado no existe, lo registra con la pass ingresada:
            this.registerNewUser()
            this.redirectToMainLogged(findViewById<EditText>(R.id.logname).text.toString())
                }
            }
        }
    }

    //Llamo al servicio de registro, pasandole el nombre y la contraseña ingresados
    fun registerNewUser() {
        this.userMgmt.registerNewUser(this, findViewById<EditText>(R.id.logname).text.toString(), findViewById<EditText>(R.id.logpass).text.toString())
    }

    //Vuelvo al main pasandole datos del usuario loggeado y el booleando para las animaciones
    private fun redirectToMainLogged(nameToDisplay: String) {
        var intentMain: Intent = Intent(this, MainActivity::class.java)
        intentMain.putExtra("isLogged", true.toString())
        intentMain.putExtra("username", nameToDisplay)
        startActivity(intentMain)
        finish()
    }

    //Valido lo que se ingresa a ver si le falta algun dato
    private fun checkRequireds(): Boolean {
        var missingOne: Boolean = false
        if(findViewById<EditText>(R.id.logname).text.isEmpty()) {
            var edittxt = findViewById<EditText>(R.id.logname)
            edittxt.setBackgroundColor(Color.parseColor("#FF603D"))
            missingOne = true;
        } else if (findViewById<EditText>(R.id.logpass).text.isEmpty()){
            var edittxt = findViewById<EditText>(R.id.logpass)
            edittxt.setBackgroundColor(Color.parseColor("#FF603D"))
            missingOne = true;
        }
        return missingOne
    }

}