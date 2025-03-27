import 'package:flutter/material.dart';
import 'package:flutter_screenutil/flutter_screenutil.dart';
import 'package:url_launcher/url_launcher.dart';

import '../../com/data/filestr.dart';
import '../../com/data/wstr.dart';
import '../s_text_v.dart';

class SetShop extends StatelessWidget {
  const SetShop({super.key});

  @override
  Widget build(BuildContext context) {
    return Column(
      mainAxisSize: MainAxisSize.min,
      crossAxisAlignment: CrossAxisAlignment.end,
      children: [
        InkWell(
          onTap: () {
            Navigator.pop(context);
          },
          child: Padding(
            padding: EdgeInsets.symmetric(horizontal: 8.w),
            child: Image.asset(
              iFile("fwwew"),
              width: 22.w,
              height: 22.w,
            ),
          ),
        ),
        Container(
            width: 410.w,
            height: 300.w,
            padding: EdgeInsets.symmetric(horizontal: 40.w),
            decoration: BoxDecoration(image: DecorationImage(image: AssetImage(iFile("heewe312")), fit: BoxFit.fill)),
            child: Column(
              children: [
                SizedBox(height: 23.w),
                STextV(text: wstr.weeg, strokeWidth: 1.w, strokeColor: const Color(0xFF8B4923), color: Colors.white, fontSize: 28.sp),
                SizedBox(height: 68.w),
                InkWell(
                  onTap: () {
                    launchUrl(Uri.parse("https://www.bing.com"));
                  },
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: [
                      Text(wstr.efrew, style: TextStyle(fontSize: 20.sp, color: const Color(0xFF613117), fontWeight: FontWeight.w900)),
                      Image.asset(
                        iFile("proior"),
                        width: 24.w,
                        height: 24.w,
                      )
                    ],
                  ),
                ),
                SizedBox(height: 35.w),
                InkWell(
                  onTap: () {
                    launchUrl(Uri.parse("mailto:krounord@aurinoviolin.com"));
                  },
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: [
                      Text(wstr.wtre, style: TextStyle(fontSize: 20.sp, color: const Color(0xFF613117), fontWeight: FontWeight.w900)),
                      Image.asset(
                        iFile("proior"),
                        width: 24.w,
                        height: 24.w,
                      )
                    ],
                  ),
                ),
              ],
            )),
      ],
    );
  }
}
