import 'package:flutter/material.dart';
import 'package:flutter_screenutil/flutter_screenutil.dart';
import 'package:lottie/lottie.dart';

import '../../com/data/filestr.dart';
import '../../com/data/wstr.dart';
import '../s_text_v.dart';

class CorrectShop extends StatelessWidget {
  final int ji;

  const CorrectShop(this.ji, {super.key});

  @override
  Widget build(BuildContext context) {
    return Column(
      mainAxisSize: MainAxisSize.min,
      children: [
        Stack(
          alignment: Alignment.center,
          children: [
            Image.asset(
              iFile("hgeww"),
              width: 270.w,
              height: 72.w,
            ),
            Image.asset(
              iFile("gherte"),
              width: 270.w,
              height: 72.w,
            )
          ],
        ),
        SizedBox(height: 25.w),
        Stack(
          clipBehavior: Clip.none,
          children: [
            Positioned(
              top: -100.w,
              left: -100.w,
              child: Lottie.asset(
                zFile("lrjiir"),
                renderCache: RenderCache.drawingCommands,
                width: 310.w,
                height: 310.w,
              ),
            ),
            Image.asset(
              iFile("ge68382"),
              width: 120.w,
              height: 120.w,
            ),
          ],
        ),
        Text("+$ji", style: TextStyle(fontSize: 28.sp, color: const Color(0xFFFFE600), fontWeight: FontWeight.w900)),
        SizedBox(height: 4.w),
        Text(wstr.rw, style: TextStyle(fontSize: 17.sp, color: Colors.white, fontWeight: FontWeight.w900)),
        SizedBox(height: 55.w),
        InkWell(
          onTap: () {
            Navigator.pop(context);
          },
          child: Stack(
            alignment: Alignment.center,
            children: [
              Image.asset(
                iFile("geweweq"),
                width: 270.w,
                height: 72.w,
              ),
              STextV(text: wstr.bcont, strokeWidth: 1.w, strokeColor: const Color(0xFF004802), color: Colors.white, fontSize: 28.sp)
            ],
          ),
        )
      ],
    );
  }
}
