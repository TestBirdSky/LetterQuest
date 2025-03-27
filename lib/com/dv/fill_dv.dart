import 'dart:convert';
import 'dart:math';

import 'package:flutter/cupertino.dart';
import 'package:wordspot/com/data/filestr.dart';

class FillDv {
  // """["Cat","Bee","Dog","Car","Bus","Pen","Cup","Hat","Sun","Run","Fly","Toy","Key","Egg","Leg","Arm","Bed","Box","Day","Gum","Joy","Oak","Pan","Pet","Pie","Run","Sad","Sea","Sky","Sun","Bug","Bat","Cow","Dry","Fox","Ice","Tap","Tea","Wet","Zap","Arm","Zip","Sun","Air","Log","Map","Boy","Able","Bank","Cold","Dent","Exit","Farm","Girl","Hope","Iron","Jump","Keep","Lamb","Menu","Note","Oath","Pink","Quiz","Rice","Soap","Teen","Undo","Vast","Wife","Yoga","Zero","Atom","Belt","City","Dusk","Edge","Fire","Gate","Help","Icon","Join","Kite","Lamp","Moon","Nail","Ooze","Park","Quiz","Rain","Salt","Taco","Undo","Vase","Wolf","Yawn","Zone","Army","Bird","Clip","Duck","Echo","Frog","Gift","Haze","Ibis","Joke","Kite","Lime","Mule","Nerd","Oven","Puff","Quip","Rake","Sack","Tilt","Undo","Vial","Wisp","Yawn","Zest","Ache","Bolt","Cash","Dive","Each","Farm","Gasp","Haze","Ibis","Judo","Kelp","Loom","Mute","Numb","Ooze","Pave","Quiz","Rank","Seed","Tilt","Undo","Vase","Wind","Yawn","Zero","Beach","Apple","Cloud","Dance","Eagle","Fairy","Giant","Horse","Juice","Karma","Lying","Music","Nanny","Olive","Piano","Queen","Ruler","Snake","Table","Urban","Vital","Water","Xenon","Yacht","Zebra","Alive","Brave","Chose","Diver","Early","Fable","Gnome","Happy","Icing","Jolly","Kneel","Latch","Mirth","Nymph","Ozone","Peach","Quiet","Radar","Safer","Tidal","Ulnar","Venom","Witty","Xerox","Zesty","Amber","Bliss","Candy","Dizzy","Ember","Fuzzy","Grasp","Happy","Icily","Jumbo","Kinky","Lumpy","Mango","Nifty","Oboes","Peach","Quick","Rumba","Silky","Tango","Unify","Viper","Wagon","Xenon","Yummy","Adept","Blink","Crash","Dingo","Evoke","Flute","Girth","Haste","Icier","Jazzy","Kudos","Loyal","Mocha","Noble","Ozone","Pouch","Quirk","Rusty","Shrug","Tummy","Unzip","Vixen","Witty"]""";
  static const _fdv =
      "WyJDYXQiLCJCZWUiLCJEb2ciLCJDYXIiLCJCdXMiLCJQZW4iLCJDdXAiLCJIYXQiLCJTdW4iLCJSdW4iLCJGbHkiLCJUb3kiLCJLZXkiLCJFZ2ciLCJMZWciLCJBcm0iLCJCZWQiLCJCb3giLCJEYXkiLCJHdW0iLCJKb3kiLCJPYWsiLCJQYW4iLCJQZXQiLCJQaWUiLCJSdW4iLCJTYWQiLCJTZWEiLCJTa3kiLCJTdW4iLCJCdWciLCJCYXQiLCJDb3ciLCJEcnkiLCJGb3giLCJJY2UiLCJUYXAiLCJUZWEiLCJXZXQiLCJaYXAiLCJBcm0iLCJaaXAiLCJTdW4iLCJBaXIiLCJMb2ciLCJNYXAiLCJCb3kiLCJBYmxlIiwiQmFuayIsIkNvbGQiLCJEZW50IiwiRXhpdCIsIkZhcm0iLCJHaXJsIiwiSG9wZSIsIklyb24iLCJKdW1wIiwiS2VlcCIsIkxhbWIiLCJNZW51IiwiTm90ZSIsIk9hdGgiLCJQaW5rIiwiUXVpeiIsIlJpY2UiLCJTb2FwIiwiVGVlbiIsIlVuZG8iLCJWYXN0IiwiV2lmZSIsIllvZ2EiLCJaZXJvIiwiQXRvbSIsIkJlbHQiLCJDaXR5IiwiRHVzayIsIkVkZ2UiLCJGaXJlIiwiR2F0ZSIsIkhlbHAiLCJJY29uIiwiSm9pbiIsIktpdGUiLCJMYW1wIiwiTW9vbiIsIk5haWwiLCJPb3plIiwiUGFyayIsIlF1aXoiLCJSYWluIiwiU2FsdCIsIlRhY28iLCJVbmRvIiwiVmFzZSIsIldvbGYiLCJZYXduIiwiWm9uZSIsIkFybXkiLCJCaXJkIiwiQ2xpcCIsIkR1Y2siLCJFY2hvIiwiRnJvZyIsIkdpZnQiLCJIYXplIiwiSWJpcyIsIkpva2UiLCJLaXRlIiwiTGltZSIsIk11bGUiLCJOZXJkIiwiT3ZlbiIsIlB1ZmYiLCJRdWlwIiwiUmFrZSIsIlNhY2siLCJUaWx0IiwiVW5kbyIsIlZpYWwiLCJXaXNwIiwiWWF3biIsIlplc3QiLCJBY2hlIiwiQm9sdCIsIkNhc2giLCJEaXZlIiwiRWFjaCIsIkZhcm0iLCJHYXNwIiwiSGF6ZSIsIkliaXMiLCJKdWRvIiwiS2VscCIsIkxvb20iLCJNdXRlIiwiTnVtYiIsIk9vemUiLCJQYXZlIiwiUXVpeiIsIlJhbmsiLCJTZWVkIiwiVGlsdCIsIlVuZG8iLCJWYXNlIiwiV2luZCIsIllhd24iLCJaZXJvIiwiQmVhY2giLCJBcHBsZSIsIkNsb3VkIiwiRGFuY2UiLCJFYWdsZSIsIkZhaXJ5IiwiR2lhbnQiLCJIb3JzZSIsIkp1aWNlIiwiS2FybWEiLCJMeWluZyIsIk11c2ljIiwiTmFubnkiLCJPbGl2ZSIsIlBpYW5vIiwiUXVlZW4iLCJSdWxlciIsIlNuYWtlIiwiVGFibGUiLCJVcmJhbiIsIlZpdGFsIiwiV2F0ZXIiLCJYZW5vbiIsIllhY2h0IiwiWmVicmEiLCJBbGl2ZSIsIkJyYXZlIiwiQ2hvc2UiLCJEaXZlciIsIkVhcmx5IiwiRmFibGUiLCJHbm9tZSIsIkhhcHB5IiwiSWNpbmciLCJKb2xseSIsIktuZWVsIiwiTGF0Y2giLCJNaXJ0aCIsIk55bXBoIiwiT3pvbmUiLCJQZWFjaCIsIlF1aWV0IiwiUmFkYXIiLCJTYWZlciIsIlRpZGFsIiwiVWxuYXIiLCJWZW5vbSIsIldpdHR5IiwiWGVyb3giLCJaZXN0eSIsIkFtYmVyIiwiQmxpc3MiLCJDYW5keSIsIkRpenp5IiwiRW1iZXIiLCJGdXp6eSIsIkdyYXNwIiwiSGFwcHkiLCJJY2lseSIsIkp1bWJvIiwiS2lua3kiLCJMdW1weSIsIk1hbmdvIiwiTmlmdHkiLCJPYm9lcyIsIlBlYWNoIiwiUXVpY2siLCJSdW1iYSIsIlNpbGt5IiwiVGFuZ28iLCJVbmlmeSIsIlZpcGVyIiwiV2Fnb24iLCJYZW5vbiIsIll1bW15IiwiQWRlcHQiLCJCbGluayIsIkNyYXNoIiwiRGluZ28iLCJFdm9rZSIsIkZsdXRlIiwiR2lydGgiLCJIYXN0ZSIsIkljaWVyIiwiSmF6enkiLCJLdWRvcyIsIkxveWFsIiwiTW9jaGEiLCJOb2JsZSIsIk96b25lIiwiUG91Y2giLCJRdWlyayIsIlJ1c3R5IiwiU2hydWciLCJUdW1teSIsIlVuemlwIiwiVml4ZW4iLCJXaXR0eSJdCg==";

  static List<String> getFillData() {
    return List<String>.from(jsonDecode(decode(_fdv)));
  }
}

class BackConfig {
  final AnimationController con;
  final String wo;
  final GlobalKey key = GlobalKey();

  double toX = 0;
  double toY = 0;
  int randomY = 0;
  bool show = true;

  BackConfig(this.con, this.wo) {
    random();
  }

  random() {
    randomY = Random().nextInt(180);
  }
}
