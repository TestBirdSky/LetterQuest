import 'package:flutter/material.dart';
import 'package:flutter_screenutil/flutter_screenutil.dart';
import 'package:wordspot/com/data/filestr.dart';
import 'package:wordspot/com/data/route.dart';
import 'package:wordspot/level_s.dart';

import 'main.dart';

class ProF extends StatefulWidget {
  const ProF({super.key});

  @override
  State<ProF> createState() => _ProFState();
}

class _ProFState extends State<ProF> with SingleTickerProviderStateMixin {
  late AnimationController animationController;
  late Animation animation;

  @override
  void initState() {
    animationController = AnimationController(vsync: this, duration: const Duration(seconds: 2));

    animation = Tween<double>(begin: 0, end: 1).chain(CurveTween(curve: Curves.ease)).animate(animationController);

    animationController.addStatusListener((status) {
      if (status == AnimationStatus.completed) {
        Navigator.pushReplacement(context, getRoute(const LevelS(), homeStr));
      }
    });

    super.initState();

    WidgetsBinding.instance.addPostFrameCallback((callback) {
      animationController.forward(from: 0);

      // Future.delayed(const Duration(seconds: 2), () {
      //   animationController.duration = const Duration(seconds: 1);
      //   animationController.forward(from: animation.value);
      // });
    });
  }

  @override
  void dispose() {
    animationController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Container(
        width: double.infinity,
        height: double.infinity,
        decoration: BoxDecoration(image: DecorationImage(image: AssetImage(iFile("reee65")), fit: BoxFit.fill)),
        child: Stack(
          alignment: Alignment.topCenter,
          children: [
            Padding(
              padding: EdgeInsets.only(top: 80.w),
              child: Image.asset(
                iFile("rgerw65"),
                width: 320.w,
                height: 320.w,
              ),
            ),
            Align(
              alignment: Alignment.bottomCenter,
              child: Padding(
                padding: EdgeInsets.only(bottom: 120.w),
                //进度条
                child: Container(
                  width: 335.w,
                  height: 24.w,
                  padding: EdgeInsets.all(2.w),
                  decoration: BoxDecoration(
                      color: const Color(0xFFB3B026),
                      borderRadius: BorderRadius.circular(30.w),
                      boxShadow: [BoxShadow(color: const Color(0xFF989519), blurRadius: 1.w)]),
                  child: ClipRRect(
                      borderRadius: BorderRadius.circular(30.w),
                      child: AnimatedBuilder(
                          animation: animation,
                          builder: (context, child) => FractionallySizedBox(
                                alignment: Alignment.centerLeft,
                                widthFactor: animation.value,
                                child: Container(
                                  width: double.infinity,
                                  height: double.infinity,
                                  decoration: BoxDecoration(
                                      borderRadius: BorderRadius.circular(29.w),
                                      gradient: const LinearGradient(
                                          begin: Alignment.topCenter,
                                          end: Alignment.bottomCenter,
                                          colors: [Color(0xFF0094FF), Color(0xFF45F4FF), Color(0xFF0094FF)],
                                          stops: [0, 0.4, 1]),
                                      boxShadow: [BoxShadow(color: const Color(0xFF0094FF), blurRadius: 0.5.w)]),
                                ),
                              ))),
                ),
              ),
            )
          ],
        ),
      ),
    );
  }
}
