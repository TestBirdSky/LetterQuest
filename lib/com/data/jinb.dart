import 'package:flutter/cupertino.dart';
import 'package:wordspot/com/data/sroy.dart';

class Jinb {
  Jinb._();

  static const SroyKey<int> _jib = SroyKey(key: "jib", d: 0);

  static var v = _jib.get();

  static putV(int nv) {
    v += nv;
    _jib.put(v);
  }

  static Function() vLis(ValueSetter l) => _jib.lis(l);
}
