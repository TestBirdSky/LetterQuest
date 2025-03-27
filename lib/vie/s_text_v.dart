import 'package:flutter/material.dart';

class STextV extends StatelessWidget {
  final String text;
  final double strokeWidth;
  final Color strokeColor;
  final Color color;
  final double fontSize;
  final FontWeight fontWeight;

  const STextV(
      {required this.text,
      required this.strokeWidth,
      required this.strokeColor,
      required this.fontSize,
      required this.color,
      this.fontWeight = FontWeight.w900,
      super.key});

  @override
  Widget build(BuildContext context) {
    return Stack(
      alignment: Alignment.center,
      children: [
        Text(text,
            style: TextStyle(
                foreground: Paint()
                  ..style = PaintingStyle.stroke
                  ..strokeWidth = strokeWidth
                  ..color = strokeColor,
                fontSize: fontSize,
                fontWeight: fontWeight)),
        Text(text, style: TextStyle(fontSize: fontSize, color: color, fontWeight: fontWeight)),
      ],
    );
  }
}
