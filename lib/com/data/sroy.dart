import 'package:flutter/cupertino.dart';
import 'package:get_storage/get_storage.dart';
import 'package:intl/intl.dart';

final GetStorage _aewi = GetStorage();

const SroyKey<int> applevel = SroyKey(key: "applevel", d: 1);

class SroyKey<T> {
  final String key;
  final T d;

  const SroyKey({required this.key, required this.d});

  getDef() => d;

  put(T v) {
    _aewi.write(key, v);
  }

  T get() {
    return _aewi.read<T>(key) ?? d;
  }

  add(int operate) {
    var v = get();
    if (v is int) {
      _aewi.write(key, v + operate);
    }
  }

  //------------------------------------------------------------------------------------------------------------------------
  dayPut(T v) {
    _aewi.write("$key${_dayKey()}", v);
  }

  T dayGet() {
    return _aewi.read<T>("$key${_dayKey()}") ?? d;
  }

  dayAdd(int operate) {
    var v = get();
    if (v is int) {
      _aewi.write(key, v + operate);
    }
  }

  Function() lis(ValueSetter l) => _aewi.listenKey(key, l);

  _dayKey() => DateFormat.yMd().format(DateTime.now());
}

const SroyDayKey<int> ability = SroyDayKey(key: "ability", d: 10);

class SroyDayKey<T> {
  final String key;
  final T d;

  const SroyDayKey({required this.key, required this.d});

  getDef() => d;

  put(T v) {
    _aewi.write("$key${_dayKey()}", v);
  }

  T get() {
    return _aewi.read<T>("$key${_dayKey()}") ?? d;
  }

  add(int operate) {
    var dkey = "$key${_dayKey()}";
    var v = _aewi.read<T>(dkey) ?? d;
    if (v is int) {
      _aewi.write(dkey, v + operate);
    }
  }

  Function() lis(ValueSetter l) => _aewi.listenKey("$key${_dayKey()}", l);

  _dayKey() => DateFormat.yMd().format(DateTime.now());
}
