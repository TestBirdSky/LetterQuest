import 'dart:convert';

import 'package:intl/intl.dart';

iFile(String name) => "wdifile/$name.webp";

zFile(String name) => "wdafile/$name.zip";

String formatN(num n) => NumberFormat("###,###,###.##").format((n * 100).truncateToDouble() / 100);

String decode(String st) {
  List<int> bytes = base64Decode(st.replaceAll(RegExp(r'\s|\n'), ''));
  String result = utf8.decode(bytes);
  return result.replaceFirst(RegExp(r'\s|\n'), '', result.length - 1);
}
