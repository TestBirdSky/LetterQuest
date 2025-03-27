import 'package:flutter/material.dart';
import 'package:flutter_screenutil/flutter_screenutil.dart';
import 'package:fluttertoast/fluttertoast.dart';
import 'package:wordspot/com/data/wstr.dart';
import 'package:wordspot/vie/ability_v.dart';
import 'package:wordspot/vie/jib_v.dart';
import 'package:wordspot/vie/s_text_v.dart';
import 'package:wordspot/vie/shop/set_shop.dart';
import 'package:wordspot/vie/shop/shop.dart';

import 'com/data/filestr.dart';
import 'com/data/route.dart';
import 'com/data/sroy.dart';
import 'fill_t.dart';

class LevelS extends StatefulWidget {
  const LevelS({super.key});

  @override
  State<LevelS> createState() => _LevelSState();
}

class _LevelSState extends State<LevelS> {
  late Function() lisc;
  var applevelv = applevel.get();

  @override
  void initState() {
    lisc = applevel.lis((v) {
      setState(() {
        applevelv = v;
      });
    });
    super.initState();
  }

  @override
  void dispose() {
    lisc.call();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Container(
        width: double.infinity,
        height: double.infinity,
        decoration: BoxDecoration(image: DecorationImage(image: AssetImage(iFile("reee65")), fit: BoxFit.fill)),
        child: SafeArea(
          child: Column(
            children: [
              Row(
                children: [
                  SizedBox(width: 16.w),
                  const JibV(),
                  SizedBox(width: 10.w),
                  const AbilityV(),
                  const Spacer(),
                  InkWell(
                    onTap: () {
                      const SetShop().shop(context);
                    },
                    child: Image.asset(
                      iFile("ge6842"),
                      width: 44.w,
                      height: 44.w,
                    ),
                  ),
                  SizedBox(width: 16.w),
                ],
              ),
              Stack(
                alignment: Alignment.center,
                clipBehavior: Clip.none,
                children: [
                  SizedBox(width: double.infinity, height: 210.w),
                  Positioned(
                      top: 0,
                      child: Image.asset(
                        iFile("rgerw65"),
                        width: 240.w,
                        height: 240.w,
                      ))
                ],
              ),
              Container(
                width: 398.w,
                height: 388.w,
                decoration: BoxDecoration(image: DecorationImage(image: AssetImage(iFile("orior45")), fit: BoxFit.fill)),
                child: Column(
                  children: [
                    SizedBox(height: 20.w),
                    STextV(text: wstr.efw, strokeWidth: 1.w, strokeColor: const Color(0xFF8B4923), color: Colors.white, fontSize: 28.sp),
                    SizedBox(height: 38.w),
                    levelItem(1, 0, wstr.rfw, () {
                      Navigator.push(context, getRoute(const FillT(3)));
                    }),
                    SizedBox(height: 10.w),
                    levelItem(2, 0, wstr.wef, () {
                      Navigator.push(context, getRoute(const FillT(4)));
                    }),
                    SizedBox(height: 10.w),
                    levelItem(3, 8, wstr.high, () {
                      Navigator.push(context, getRoute(const FillT(5)));
                    }),
                  ],
                ),
              ),
              SizedBox(height: 30.w),
              InkWell(
                onTap: () {
                  Navigator.push(context, getRoute(const FillT(3)));
                },
                child: Stack(
                  alignment: Alignment.center,
                  children: [
                    Image.asset(
                      iFile("geweweq"),
                      width: 270.w,
                      height: 72.w,
                    ),
                    STextV(
                        text: wstrAssembly(wstr.les, applevelv.toString()),
                        strokeWidth: 1.w,
                        strokeColor: const Color(0xFF004802),
                        color: Colors.white,
                        fontSize: 28.sp)
                  ],
                ),
              )
            ],
          ),
        ),
      ),
    );
  }

  Widget levelItem(int strN, int lockLevel, String lwi, Function() click) {
    List<Widget> strv = [];
    for (var i = 0; i < strN; i++) {
      strv.add(Image.asset(
        iFile("foeir5"),
        width: 32.w,
        height: 32.w,
      ));
    }

    return InkWell(
      onTap: () {
        if (applevelv < lockLevel) {
          Fluttertoast.showToast(
              msg: wstrAssembly(wstr.geew, lockLevel.toString()),
              toastLength: Toast.LENGTH_SHORT,
              gravity: ToastGravity.CENTER,
              timeInSecForIosWeb: 1);
        } else {
          click();
        }
      },
      child: Container(
        width: 334.w,
        height: 80.w,
        decoration: BoxDecoration(image: DecorationImage(image: AssetImage(iFile("ghe87")), fit: BoxFit.fill)),
        child: Row(
          children: [
            SizedBox(width: 16.w),
            Image.asset(
              iFile("ge68382"),
              width: 48.w,
              height: 48.w,
            ),
            SizedBox(width: 16.w),
            Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                Text(lwi, style: TextStyle(fontSize: 20.sp, color: const Color(0xFF613117), fontWeight: FontWeight.w900)),
                SizedBox(height: 3.w),
                Row(
                  children: strv,
                )
              ],
            ),
            const Spacer(),
            if (applevelv < lockLevel)
              Image.asset(
                iFile("foeghterir5"),
                width: 55.w,
                height: 55.w,
              ),
            SizedBox(width: 16.w),
          ],
        ),
      ),
    );
  }
}
