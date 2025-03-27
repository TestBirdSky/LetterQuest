import 'package:flutter/material.dart';
import 'package:flutter_screenutil/flutter_screenutil.dart';
import 'package:get_storage/get_storage.dart';
import 'package:wordspot/pro_f.dart';

//不必考虑设置按钮会因ab bao导致回退错误，因为同一时刻只会有一个携带此参数的主页
const String homeStr = "efwfg";

void main() async {
  await GetStorage.init();

  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return ScreenUtilInit(
        designSize: const Size(430, 930),
        builder: (context, child) {
          return MaterialApp(
            theme: ThemeData(
              splashColor: Colors.transparent,
              highlightColor: Colors.transparent,
              useMaterial3: true,
            ),
            home: const ProF(),
          );
        });
  }
}
