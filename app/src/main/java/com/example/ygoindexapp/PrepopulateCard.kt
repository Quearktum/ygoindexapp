package com.example.ygoindexapp

import android.content.Context
import android.util.Log
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.ygoindexapp.data.local.Card
import com.example.ygoindexapp.data.local.CardDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONException
import timber.log.Timber
import java.io.BufferedReader

class PrepopulateCard(private val context: Context) : RoomDatabase.Callback() {
    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        CoroutineScope(Dispatchers.IO).launch {
            fillWithStartingCards(context)
        }
    }

    //loading data from a JSON file
    fun loadJSONArray(context: Context): JSONArray? {

        val inputStream = context.resources.openRawResource(R.raw.cardinfoprepopulate)

        BufferedReader(inputStream.reader()).use {
            return JSONArray(it.readText())
        }
    }

    //Prepopulate the database
    suspend fun fillWithStartingCards(context: Context) {

        val dao = CardDatabase.getInstance(context)?.dao

        try {
            val cards = loadJSONArray(context)
            if (cards != null) {
                for (i in 0 until cards.length()) {
                    val item = cards.getJSONObject(i)

                    val id = item.getString("id")
                    val name = item.getString("name")
                    val type = item.getString("type")
                    val desc = item.getString("desc")
                    val race = item.getString("race")
                    var atk = ""
                    var def = ""
                    var level = ""
                    var attribute = ""

                    //Pass monster information if the type of the card is monster
                    if (type.contains("Monster")){
                        atk = item.getString("atk")

                        if(!type.contains("Link")){
                            def = item.getString("def")
                            level = item.getString("level")
                        }

                        attribute = item.getString("attribute")
                    }

                    val card = Card(
                        name, type, desc, race.toString(), atk, def, level, attribute
                    )

                    dao?.insertCard(card)
                }
            }
        } catch (e: JSONException) {
            Timber.d("fillWithStartingNotes: $e")
        }
    }
}
