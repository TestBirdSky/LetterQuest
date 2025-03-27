import 'package:flutter/material.dart';

getRoute(Widget page, [String? name]) => MaterialPageRoute(
      settings: RouteSettings(name: name),
      builder: (context) => page,
    );
