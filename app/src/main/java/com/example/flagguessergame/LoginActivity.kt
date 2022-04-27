package com.example.flagguessergame

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

        //insertTestUser()
        //getRanking()

    }

    fun goToMainActivity(view: View) {
        var intentMain: Intent = Intent(this, MainActivity::class.java)
        /*intentMain.putExtra("isLogged","true")
        intentMain.putExtra("username","Nakio")
        startActivity(intentMain)*/
        finish()
    }

    fun insertNewUser() {
        val admin = SQLiteHelper(this,"GameDB", null, 1)
        val bd = admin.writableDatabase
        val newUser = ContentValues()
        newUser.put("name", "")
        newUser.put("password", "")
        newUser.put("score", 0) //cambiar por el valor tomado de la pantalla
        bd.insert("Users", null, newUser)
        bd.close()
    }

    /*fun insertTestUser() {
        val admin = SQLiteHelper(this,"GameDB", null, 1)
        val bd = admin.writableDatabase
        val newUser = ContentValues()
        newUser.put("name", "Ditto")
        newUser.put("password", "ditto123")
        newUser.put("score", 100) //cambiar por el valor tomado de la pantalla
        try{
            bd.insert("Users", null, newUser)
        } catch(ex: Exception) {
            print(ex.message)
        }

        bd.close()
    }*/

    fun getRanking() {
        /*val query: String = "SELECT name, score FROM Users";

        //https://developer.android.com/training/data-storage/sqlite#kotlin


        val admin = SQLiteHelper(this, "Users", null, 1)
        val bd = admin.writableDatabase

        //var q: String = "SELECT name FROM sqlite_master WHERE type='table' AND name='Users'"
        //var checktable = bd.rawQuery(q, null)

        //if(checktable == null) {
        //    print("no existe")
        //}

        var filas = bd.rawQuery("select name, score from Users", null)
        if (!filas.equals(null)) {
            var r = filas.moveToNext()
            Toast.makeText(this, "Hay 1 registro en la tabla al menos", Toast.LENGTH_LONG).show()
        } else
            Toast.makeText(this, "No existe un artículo con dicha descripción", Toast.LENGTH_SHORT).show()
        bd.close()*/

        var bestPlayers: List<UserCRUD.User> = userMgmt.getTopTen(this)

        //agregarlo dentro de un if validANDO la cantidad luego

        var counter: Int = 1
        bestPlayers.forEach {
            user -> this.buildRankingRegistry(user, counter)
        }

    }

    private fun buildRankingRegistry(user: UserCRUD.User, counter: Int) {

    }

    fun eraseDatabase() {
        val name = findViewById<EditText>(R.id.logname).text.toString()
        val pass = findViewById<EditText>(R.id.logpass).text.toString()
        //excluir este nombrede usuario de los posibles, agregar validación en donde corresponda
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

    fun eraseUser() {
        if(!checkRequireds()) {
            userMgmt.eraseByName(this, findViewById<EditText>(R.id.logname).text.toString(), findViewById<EditText>(R.id.logpass).text.toString())
        }
    }

    fun checkIfUserExists(): Boolean {
        return userMgmt.exists(this, findViewById<EditText>(R.id.logname).text.toString())
    }

    fun checkIfPasswordMatches(): Boolean {
        return userMgmt.userMatchesPass(this, findViewById<EditText>(R.id.logname).text.toString(), findViewById<EditText>(R.id.logpass).text.toString())
    }

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

    fun registerNewUser() {
        this.userMgmt.registerNewUser(this, findViewById<EditText>(R.id.logname).text.toString(), findViewById<EditText>(R.id.logpass).text.toString())
    }

    private fun redirectToMainLogged(nameToDisplay: String) {
        var intentMain: Intent = Intent(this, MainActivity::class.java)
        intentMain.putExtra("isLogged", true.toString())
        intentMain.putExtra("username", nameToDisplay)
        startActivity(intentMain)
        finish()
    }

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