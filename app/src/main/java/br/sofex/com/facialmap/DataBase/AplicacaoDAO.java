package br.sofex.com.facialmap.DataBase;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

import br.sofex.com.facialmap.Entidades.Aplicacao;

@Dao
public interface AplicacaoDAO {

    //TODO: method to insert a record in Table
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAplicacao(Aplicacao aplicacao);

}
