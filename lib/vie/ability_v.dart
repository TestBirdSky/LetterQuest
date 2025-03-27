import 'package:flutter/cupertino.dart';
import 'package:flutter_screenutil/flutter_screenutil.dart';

import '../com/data/filestr.dart';
import '../com/data/sroy.dart';

class AbilityV extends StatefulWidget {
  const AbilityV({super.key});

  @override
  State<AbilityV> createState() => _JibVState();
}

class _JibVState extends State<AbilityV> {
  late Function() lisc;
  int abv = ability.get();

  @override
  void initState() {
    super.initState();

    lisc = ability.lis((v) {
      setState(() {
        abv = v;
      });
    });
  }

  @override
  void dispose() {
    lisc.call();
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
                iFile("ge6840"),
                fit: BoxFit.fill,
                width: 62.w,
                height: 30.w,
              ),
              Text(abv.toString(), style: TextStyle(fontSize: 17.sp, color: const Color(0xFF613117), fontWeight: FontWeight.w900))
            ],
          ),
        ),
        Image.asset(
          iFile("ge6841"),
          width: 33.w,
          height: 33.w,
        ),
      ],
    );
  }
}
