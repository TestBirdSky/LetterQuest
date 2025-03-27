import 'package:flutter/cupertino.dart';

Wstr wstr = Wstr();

String wstrAssembly(String s, String assemblys) {
  var newS = "";
  for (var element in s.characters) {
    if (element == "#") {
      newS += assemblys;
    } else {
      newS += element;
    }
  }
  return newS;
}

class Wstr {
  String efw = "Challenge";
  String rfw = "Easy";
  String wef = "Middle";
  String high = "High";
  String rw = "Right Word";
  String les = "Level #";
  String bok = "OK";
  String ino = "I Know";
  String bcont = "Continue";
  String warn = "Warn";
  String weeg = "Setting";
  String sorry = "Sorry";
  String efrew = "Privacy Policy";
  String wtre = "Contact us";
  String wrttw = "The opportunity to write lyrics has been used up today Please try again tomorrow.";
  String wfrre = "Your hint opportunities have been used up";
  String geew = "Unlocked at level #";
}
