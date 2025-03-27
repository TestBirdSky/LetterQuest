import 'package:flutter/material.dart';
import 'package:flutter_screenutil/flutter_screenutil.dart';
import 'package:lottie/lottie.dart';

import '../../com/data/filestr.dart';
import '../../com/data/wstr.dart';
import '../s_text_v.dart';

class AbilityShop extends StatelessWidget {
  const AbilityShop({super.key});

  @override
  Widget build(BuildContext context) {
    return Container(
        width: 418.w,
        height: 510.w,
        padding: EdgeInsets.symmetric(horizontal: 35.w),
        decoration: BoxDecoration(image: DecorationImage(image: AssetImage(iFile("heewe510")), fit: BoxFit.fill)),
        child: Column(
          children: [
            SizedBox(height: 23.w),
            STextV(text: wstr.warn, strokeWidth: 1.w, strokeColor: const Color(0xFF8B4923), color: Colors.white, fontSize: 28.sp),
            SizedBox(height: 90.w),
            Stack(
              alignment: Alignment.bottomRight,
              clipBehavior: Clip.none,
              children: [
                SizedBox(width: 80.w, height: 120.w),
                Positioned(
                  top: -100.w,
                  left: -115.w,
                  child: Lottie.asset(
                    zFile("jrtee"),
                    renderCache: RenderCache.drawingCommands,
                    width: 310.w,
                    height: 310.w,
                  ),
                ),
                Positioned(
                  top: 0,
                  left: -20.w,
                  child: Image.asset(
                    iFile("tyt55"),
                    width: 120.w,
                    height: 120.w,
                  ),
                ),
                STextV(text: "x0", strokeWidth: 3.w, strokeColor: const Color(0xFFC52424), color: Colors.white, fontSize: 20.sp),
              ],
            ),
            SizedBox(height: 18.w),
            Text(wstr.sorry, style: TextStyle(fontSize: 20.sp, color: const Color(0xFF613117), fontWeight: FontWeight.w900)),
            SizedBox(height: 2.w),
            Text(wstr.wrttw,
                textAlign: TextAlign.center,
                style: TextStyle(fontSize: 15.sp, color: const Color(0xFF613117), fontWeight: FontWeight.w700)),
            SizedBox(height: 27.w),
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
                  STextV(text: wstr.bok, strokeWidth: 1.w, strokeColor: const Color(0xFF004802), color: Colors.white, fontSize: 28.sp)
                ],
              ),
            )
          ],
        ));
  }
}
