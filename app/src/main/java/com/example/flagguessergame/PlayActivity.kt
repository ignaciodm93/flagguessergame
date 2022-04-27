package com.example.flagguessergame

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.squareup.picasso.Picasso

class PlayActivity : AppCompatActivity() {

    private val userMgmt = UserCRUD()

    var countryOptions: MutableList<String> = mutableListOf()   //incluyendo la verdadera
    var countriesList: Array<String> = arrayOf()
    var flagSelected: String = ""
    var guessedFlags: MutableList<String> = mutableListOf() //lista de paises respondidos correctamente

    var currentPlayer: String = ""
    var score: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play)

        //sacar de aca luego, y tomarlo como el finally despues de intentar acertar, al margen del resultado
        val botonDeBanderas = findViewById<Button>(R.id.nextbtn)
        botonDeBanderas.setOnClickListener{
        prepareNewRound()
        }
        initVars()
        initPlayerVars()
    }

    fun redirectToMain(view: View) {
        //agregar salvar antes
        var intentMain: Intent = Intent(this, MainActivity::class.java)
        startActivity(intentMain)
    }

    private fun initVars() {
        fillList()
        prepareNewRound()
    }

    private fun initPlayerVars() {
        //this.currentPlayer = findViewById<>()
        this.score =  intent.getIntExtra("score", 0)
        this.currentPlayer = intent.getStringExtra("username").toString()
        findViewById<TextView>(R.id.playertxt).text = currentPlayer
    }

    private fun prepareNewRound() {
        emptyLastOptionsUsed()
        //evito que vuelva a salir una bandera correcta como opcion correcta nuevamente
        do{
            getRandomFlag()
            flagSelected = flagSelected.replace("%20", " ")
        } while(this.guessedFlags.contains(this.flagSelected))

        selectWrongOptions(flagSelected)
        fillButtons()
    }

    fun emptyLastOptionsUsed() {
        if(this.countryOptions.isNotEmpty()) {
            this.countryOptions.removeAll(this.countryOptions)
        }
    }

    fun getRandomFlag() {
        flagSelected = countriesList.random().replace(" ", "%20")
        var img = findViewById<ImageView>(R.id.image_view)
        var url = "https://countryflagsapi.com/png/$flagSelected"
        //prueba, borrar luego:
        //findViewById<EditText>(R.id.idref).setText(flagSelected)
        Picasso.with(this).load(url).into(img)
    }

    private fun fillList() {
        //Lleno la lista de banderas.
        countriesList = arrayOf(
            "United Arab Emirates", "Andorra", "Afghanistan", "Antigua And Barbuda", "Anguilla", "Albania", "Armenia", "Angola", "Antarctica", "Argentina", "American Samoa", "Austria", "Australia", "Aruba", "åland Islands", "Azerbaijan", "Bosnia And Herzegovina", "Barbados", "Bangladesh", "Belgium", "Burkina Faso", "Bulgaria", "Bahrain", "Burundi", "Benin", "Saint Barthélemy", "Bermuda", "Brunei Darussalam", "Bolivia", "Bonaire", "Brazil", "The Bahamas", "Bhutan", "Botswana", "Belarus", "Belize", "Canada", "The Cocos Islands", "Democratic Republic Of Congo", "The Central African Republic", "Congo", "Switzerland", "Côte D'ivoire", "The Cook Islands", "Chile", "Cameroon", "China", "Colombia", "Costa Rica", "Cuba", "Cabo Verde", "Curaçao", "Christmas Island", "Cyprus", "Czechia", "Germany", "Djibouti", "Denmark", "Dominica", "Dominican Republic", "Algeria", "Ecuador", "Estonia", "Egypt", "Western Sahara", "Eritrea", "Spain", "Ethiopia", "European Union", "Finland", "Fiji", "The Falkland Islands", "The Federated States Of Micronesia", "The Faroe Islands", "France", "Gabon", "England", "Northern Ireland", "Scotland", "Wales", "Grenada", "Georgia", "French Guiana", "Guernsey", "Ghana", "Gibraltar", "Greenland", "Gambia", "Guinea", "Equatorial Guinea", "Greece", "South Georgia And The South Sandwich Islands", "Guatemala", "Guam", "Guinea-bissau", "Guyana", "Hong Kong", "Heard Island And Mcdonald Islands", "Honduras", "Croatia", "Haiti", "Hungary", "Indonesia", "Ireland", "Israel", "Isle Of Man", "India", "The British Indian Ocean Territory", "Iraq", "Iran", "Iceland", "Italy", "Jersey", "Jamaica", "Jordan", "Japan", "Kenya", "Kyrgyzstan", "Cambodia", "Kiribati", "The Comoros", "Saint Kitts And Nevis", "The Democratic People's Republic Of Korea", "The Republic Of Korea", "Kuwait", "The Cayman Islands", "Kazakhstan", "The Lao People's Democratic Republic", "Lebanon", "Saint Lucia", "Liechtenstein", "Sri Lanka", "Liberia", "Lesotho", "Lithuania", "Luxembourg", "Latvia", "Libya", "Morocco", "Monaco", "The Republic Of Moldova", "Montenegro", "Madagascar", "The Marshall Islands", "Republic Of North Macedonia", "Mali", "Myanmar", "Mongolia", "Macao", "The Northern Mariana Islands", "Martinique", "Mauritania", "Montserrat", "Malta", "Mauritius", "Maldives", "Malawi", "Mexico", "Malaysia", "Mozambique", "Namibia", "New Caledonia", "Niger", "Norfolk Island", "Nigeria", "Nicaragua", "Netherlands", "Norway", "Nepal", "Nauru", "Niue", "New Zealand", "Oman", "Panama", "Peru", "French Polynesia", "Papua New Guinea", "Philippines", "Pakistan", "Poland", "Saint Pierre And Miquelon", "Pitcairn", "Puerto Rico", "Palestine", "Portugal", "Palau", "Paraguay", "Qatar", "Romania", "Serbia", "Russian Federation", "Rwanda", "Saudi Arabia", "Solomon Islands", "Seychelles", "Sudan", "Sweden", "Singapore", "Saint Helena", "Slovenia", "Slovakia", "Sierra Leone", "San Marino", "Senegal", "Senegal", "Suriname", "South Sudan", "Sao Tome And Principe", "El Salvador", "Sint Maarten", "Syrian Arab Republic", "Eswatini", "The Turks And Caicos Islands", "Chad", "The French Southern Territories", "Togo", "Thailand", "Tajikistan", "Tokelau", "Timor-leste", "Turkmenistan", "Tunisia", "Tonga", "Turkey", "Trinidad And Tobago", "Tuvalu", "Taiwan", "United Republic Of Tanzania", "Ukraine", "Uganda", "The United States Of America", "Uruguay", "Uzbekistan", "The Holy See", "Saint Vincent And The Grenadines", "Venezuela", "British Virgin Islands", "Us Virgin Islands", "Viet Nam", "Vanuatu", "Wallis And Futuna", "Samoa", "Kosovo", "Yemen", "Mayotte", "South Africa", "South Africa", "Zimbabwe"
        )
    }

    fun selectWrongOptions(correct: String) {
        //Agarro 3 opciones de la lista excluyendo la correcta
        var count = 0;
        do {
            var option = countriesList.random()
            if(option != correct && !countryOptions.contains(option)) {
                countryOptions.add(option)
                count++;
            }
        } while (count < 3)
        countryOptions.add(correct)
    }

    private fun fillButtons() {
        //Randomizo el orden y setteo las opciones en los botones
        countryOptions.shuffle()

        //Asigno cada uno de los 4 elementos a un id de boton
        findViewById<Button>(R.id.b1).text = clearText(countryOptions[0])
        findViewById<Button>(R.id.b2).text = clearText(countryOptions[1])
        findViewById<Button>(R.id.b3).text = clearText(countryOptions[2])
        findViewById<Button>(R.id.b4).text = clearText(countryOptions[3])
    }

    private fun clearText(textToClear: String): String {
        return textToClear.replace("%20", " ")
    }

    fun checkAnswer(view: View) {
        var selectedOption: Button = view as Button
        flagSelected = flagSelected.replace("%20", " ")

        if( (selectedOption.getText().equals(this.flagSelected)) ) {
            score += 1
            findViewById<EditText>(R.id.score).setText(score.toString())
            excludeGuessedFlag(this.flagSelected)
            Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show()
        }
        else {
            Toast.makeText(this, "Wrong! The answer was $flagSelected", Toast.LENGTH_LONG).show()
        }

        //Reparto de nuevo
        prepareNewRound()
    }

    //Si la respuesta fue correcta, agrego la misma a una lista para que no vuelva a ser la opcion correcta.
    fun excludeGuessedFlag(guessedFlag: String) {
        this.guessedFlags.add(guessedFlag)
    }

    fun saveScore(view: View) {
        userMgmt.updateScore(PlayActivity(), this.currentPlayer, this.score)
    }
}