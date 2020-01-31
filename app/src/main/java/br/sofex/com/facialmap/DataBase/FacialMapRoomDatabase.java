package br.sofex.com.facialmap.DataBase;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import br.sofex.com.facialmap.Entidades.Aplicacao;
import br.sofex.com.facialmap.Entidades.Mapa;
import br.sofex.com.facialmap.Entidades.Paciente;
import br.sofex.com.facialmap.Entidades.Ponto;
import br.sofex.com.facialmap.Entidades.Usuario;

@Database(entities = {Usuario.class, Paciente.class, Mapa.class, Ponto.class, Aplicacao.class}, version = 1,exportSchema = false)  //exportSchema = true
public abstract class FacialMapRoomDatabase extends RoomDatabase {
    Context mContext;

    public abstract UsuarioDAO usuarioDao();
    public abstract PacienteDAO pacienteDao();
    public abstract MapaDAO mapaDao();
    public abstract PontoDAO pontoDao();
    public abstract AplicacaoDAO aplicacaoDao();
    private static volatile FacialMapRoomDatabase INSTANCE;

    //TODO : Global
    // significa apenas que o identificador em questão é declarado no escopo do arquivo.
    // Existem escopos diferentes, chamados função (onde são definidos rótulos goto),
    // arquivo (onde residem os globais), bloco (onde residem as variáveis ​​locais normais) e protótipo da função (onde residem os parâmetros da função).
    // Este conceito existe apenas para estruturar a visibilidade dos identificadores. Não tem nada a ver com otimizações.

    //TODO : Static
    // Se dois Threads (suponha que o Thread 1 e o Thread 2) estejam acessando o mesmo objeto e atualizando uma variável declarada como estática,
    // significa que o Thread 1 e o Thread 2 podem fazer sua própria cópia local do mesmo objeto (incluindo variáveis ​​estáticas) em suas respectivo cache,
    // portanto, a atualização do Thread 1 para a variável estática em seu cache local não refletirá na variável estática do cache do Thread 2.
    // As variáveis ​​estáticas são usadas no Contexto do Objeto em que a atualização por um objeto refletiria em todos os outros objetos da mesma classe,
    // mas não no contexto do Encadeamento em que a atualização de um encadeamento na variável estática refletirá as alterações imediatamente
    // em todos os encadeamentos (em cache local).

    //TODO : Volatile Static Se dois Threads (suponha que o Thread 1 e o Thread 2) estejam acessando o mesmo objeto e atualizando uma variável declarada como volátil,
    // isso significa que o Thread 1 e o Thread 2 podem fazer seu próprio cache local do Objeto, exceto a variável declarada como volátil. Portanto,
    // a variável volátil terá apenas uma cópia principal que será atualizada por diferentes threads e a atualização por um thread na variável volátil será refletida
    // imediatamente no outro Thread. Portanto, a variável volátil é usada no contexto do Thread.
    public static FacialMapRoomDatabase getDatabaseMapa(final Context context) {
        if (INSTANCE == null) {
            synchronized (FacialMapRoomDatabase.class) {
                if (INSTANCE == null) {
                  INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                  FacialMapRoomDatabase.class, "FacialMap.db").build();
                }
            }
        }
        return INSTANCE;
    }
    /*private static volatile PessoaRoomDatabase INSTANCE;

    public static PessoaRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (PessoaRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    PessoaRoomDatabase.class, "Pessoa.db").build();
                }
            }
        }
        return INSTANCE;
    }

    private static PessoaRoomDatabase create(final Context context) {
        return Room.databaseBuilder(
                context,
                PessoaRoomDatabase.class,
                "repoDatabase.db").build();
    }

    public PessoaRoomDatabase(Context context){
        this.mContext = context;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }*/
    public static void destroyInstance() {
        INSTANCE = null;
    }
}
