package org.fabi.monvotodroid.dao;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import org.fabi.monvotodroid.model.VDQuestion;
import org.fabi.monvotodroid.model.VDVote;

@Database(entities = {VDQuestion.class, VDVote.class}, version = 1)
public abstract class MaBD extends RoomDatabase {
    public abstract DemoDAO dao();
}
