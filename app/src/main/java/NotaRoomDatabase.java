import android.content.Context;

import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.cm_backup.entities.Nota;

@Database(entities = {Nota.class}, version = 1, exportSchema = false)
public abstract class NotaRoomDatabase extends RoomDatabase {

    public abstract NotaDao notaDao();
    private static NotaRoomDatabase INSTANCE;

    //para que a classe seja do tipo singleton, ou seja, só possa ser instanciada uma vez
    public static NotaRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (NotaRoomDatabase.class) {
                if (INSTANCE == null) {
                    // Criação da base de dados
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            NotaRoomDatabase.class, "nota_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}


