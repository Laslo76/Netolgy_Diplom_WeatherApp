package ru.netology.weatherapp.data.local;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class WeatherDao_Impl implements WeatherDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<WeatherEntity> __insertionAdapterOfWeatherEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteForCity;

  public WeatherDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfWeatherEntity = new EntityInsertionAdapter<WeatherEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `forecast_days` (`date`,`city`,`tempAvg`,`tempMin`,`tempMax`,`humidity`,`pressure`,`windSpeed`,`windDirection`,`precipitationProbability`,`cachedAt`) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final WeatherEntity entity) {
        statement.bindString(1, entity.getDate());
        statement.bindString(2, entity.getCity());
        statement.bindDouble(3, entity.getTempAvg());
        statement.bindDouble(4, entity.getTempMin());
        statement.bindDouble(5, entity.getTempMax());
        statement.bindDouble(6, entity.getHumidity());
        statement.bindDouble(7, entity.getPressure());
        statement.bindLong(8, entity.getWindSpeed());
        statement.bindString(9, entity.getWindDirection());
        statement.bindDouble(10, entity.getPrecipitationProbability());
        statement.bindLong(11, entity.getCachedAt());
      }
    };
    this.__preparedStmtOfDeleteForCity = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM forecast_days WHERE city = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertAll(final List<WeatherEntity> forecasts,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfWeatherEntity.insert(forecasts);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteForCity(final String city, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteForCity.acquire();
        int _argIndex = 1;
        _stmt.bindString(_argIndex, city);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteForCity.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object getForecastForCity(final String city,
      final Continuation<? super List<WeatherEntity>> $completion) {
    final String _sql = "SELECT * FROM forecast_days WHERE city = ? ORDER BY date ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, city);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<WeatherEntity>>() {
      @Override
      @NonNull
      public List<WeatherEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfCity = CursorUtil.getColumnIndexOrThrow(_cursor, "city");
          final int _cursorIndexOfTempAvg = CursorUtil.getColumnIndexOrThrow(_cursor, "tempAvg");
          final int _cursorIndexOfTempMin = CursorUtil.getColumnIndexOrThrow(_cursor, "tempMin");
          final int _cursorIndexOfTempMax = CursorUtil.getColumnIndexOrThrow(_cursor, "tempMax");
          final int _cursorIndexOfHumidity = CursorUtil.getColumnIndexOrThrow(_cursor, "humidity");
          final int _cursorIndexOfPressure = CursorUtil.getColumnIndexOrThrow(_cursor, "pressure");
          final int _cursorIndexOfWindSpeed = CursorUtil.getColumnIndexOrThrow(_cursor, "windSpeed");
          final int _cursorIndexOfWindDirection = CursorUtil.getColumnIndexOrThrow(_cursor, "windDirection");
          final int _cursorIndexOfPrecipitationProbability = CursorUtil.getColumnIndexOrThrow(_cursor, "precipitationProbability");
          final int _cursorIndexOfCachedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "cachedAt");
          final List<WeatherEntity> _result = new ArrayList<WeatherEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final WeatherEntity _item;
            final String _tmpDate;
            _tmpDate = _cursor.getString(_cursorIndexOfDate);
            final String _tmpCity;
            _tmpCity = _cursor.getString(_cursorIndexOfCity);
            final double _tmpTempAvg;
            _tmpTempAvg = _cursor.getDouble(_cursorIndexOfTempAvg);
            final double _tmpTempMin;
            _tmpTempMin = _cursor.getDouble(_cursorIndexOfTempMin);
            final double _tmpTempMax;
            _tmpTempMax = _cursor.getDouble(_cursorIndexOfTempMax);
            final double _tmpHumidity;
            _tmpHumidity = _cursor.getDouble(_cursorIndexOfHumidity);
            final double _tmpPressure;
            _tmpPressure = _cursor.getDouble(_cursorIndexOfPressure);
            final int _tmpWindSpeed;
            _tmpWindSpeed = _cursor.getInt(_cursorIndexOfWindSpeed);
            final String _tmpWindDirection;
            _tmpWindDirection = _cursor.getString(_cursorIndexOfWindDirection);
            final double _tmpPrecipitationProbability;
            _tmpPrecipitationProbability = _cursor.getDouble(_cursorIndexOfPrecipitationProbability);
            final long _tmpCachedAt;
            _tmpCachedAt = _cursor.getLong(_cursorIndexOfCachedAt);
            _item = new WeatherEntity(_tmpDate,_tmpCity,_tmpTempAvg,_tmpTempMin,_tmpTempMax,_tmpHumidity,_tmpPressure,_tmpWindSpeed,_tmpWindDirection,_tmpPrecipitationProbability,_tmpCachedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getLastCacheTime(final String city, final Continuation<? super Long> $completion) {
    final String _sql = "SELECT MAX(cachedAt) FROM forecast_days WHERE city = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, city);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Long>() {
      @Override
      @Nullable
      public Long call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Long _result;
          if (_cursor.moveToFirst()) {
            final Long _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(0);
            }
            _result = _tmp;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
