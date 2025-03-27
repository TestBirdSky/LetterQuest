import 'package:flutter/cupertino.dart';
import 'package:flutter_screenutil/flutter_screenutil.dart';

import '../com/data/filestr.dart';
import '../com/data/jinb.dart';

class JibV extends StatefulWidget {
  const JibV({super.key});

  @override
  State<JibV> createState() => _JibVState();
}

class _JibVState extends State<JibV> with SingleTickerProviderStateMixin {
  var v = Jinb.v;
  late AnimationController animationController;
  late Animation animation;
  late Function() lisc;

  @override
  void initState() {
    animationController = AnimationController(vsync: this, duration: const Duration(milliseconds: 600));

    animation = Tween<double>(begin: 0, end: 1).chain(CurveTween(curve: Curves.linear)).animate(animationController);

    animationController.addListener(() {
      setState(() {});
    });

    animationController.addStatusListener((status) {
      if (status == AnimationStatus.completed) {
        setState(() {
          v = Jinb.v;
        });
      }
    });

    super.initState();

    lisc = Jinb.vLis((v) {
      animationController.forward(from: 0);
    });
  }

  @override
  void dispose() {
    lisc.call();
    animationController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Stack(
      alignment: Alignment.centerLeft,
      children: [
        Padding(
          padding: EdgeInsets.only(left: 15.w),
          child: Stack(
            alignment: Alignment.center,
            children: [
              Image.asset(
                iFile("ge6839"),
                fit: BoxFit.fill,
                width: 111.w,
                height: 30.w,
              ),
              Text(formatN(animation.value * (Jinb.v - v) + v),
                  style: TextStyle(fontSize: 17.sp, color: const Color(0xFF613117), fontWeight: FontWeight.w900))
            ],
          ),
        ),
        Image.asset(
          iFile("ge68382"),
          width: 33.w,
          height: 33.w,
        ),
      ],
    );
  }
}
