import 'package:flutter/material.dart';

extension ShopExt on Widget {
  Future shop(BuildContext context, [bool dis = false]) {
    return showDialog(
        context: context,
        barrierDismissible: dis,
        barrierColor: Colors.black.withOpacity(0.9),
        builder: (context) {
          var dialog = Dialog(insetPadding: EdgeInsets.zero, backgroundColor: Colors.transparent, child: this);
          return PopScope(canPop: dis, child: dialog);
        });
  }
}
