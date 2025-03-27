import 'dart:async';
import 'dart:math';

import 'package:flutter/material.dart';
import 'package:flutter_screenutil/flutter_screenutil.dart';
import 'package:lottie/lottie.dart';
import 'package:shimmer/shimmer.dart';
import 'package:wordspot/com/data/jinb.dart';
import 'package:wordspot/vie/ability_v.dart';
import 'package:wordspot/vie/jib_v.dart';
import 'package:wordspot/vie/s_text_v.dart';
import 'package:wordspot/vie/shop/ability_shop.dart';
import 'package:wordspot/vie/shop/correct_shop.dart';
import 'package:wordspot/vie/shop/help_shop.dart';
import 'package:wordspot/vie/shop/set_shop.dart';
import 'package:wordspot/vie/shop/shop.dart';

import 'com/data/filestr.dart';
import 'com/data/sroy.dart';
import 'com/data/wstr.dart';
import 'com/dv/fill_dv.dart';

class FillT extends StatefulWidget {
  final int wordN;

  const FillT(this.wordN, {super.key});

  @override
  State<FillT> createState() => _FillTState();
}

class _FillTState extends State<FillT> with TickerProviderStateMixin {
  late Function() lisc;
  var applevelv = applevel.get();

  late SroyKey<int> startPosKey;
  late SroyKey<int> cPosMax5Key;

  late List<String> wordS;

  bool allowSelect = false;
  late List<String> curWords;
  int selectIndex = 0;
  List<String> selectWords = [];
  List<BackConfig> banks = [];

  bool showHelp = false;
  bool showHighlight = false;
  final GlobalKey selectKey = GlobalKey();

  late SroyDayKey<int> helpKey;

  @override
  void initState() {
    lisc = applevel.lis((v) {
      setState(() {
        applevelv = v;
      });
    });

    startPosKey = SroyKey(key: "startPos${widget.wordN}", d: 0);
    cPosMax5Key = const SroyKey(key: "cPosMax5", d: 5);
    helpKey = const SroyDayKey(key: "fhelp", d: 10);

    wordS = FillDv.getFillData();
    wordS.removeWhere((element) => element.length != widget.wordN);

    update();
    super.initState();
  }

  @override
  void dispose() {
    lisc.call();
    clearBack();
    super.dispose();
  }

  clearBack() {
    for (var b in banks) {
      b.con.dispose();
    }
    banks.clear();
  }

  update() {
    if (!mounted) return;

    setState(() {
      var pos = startPosKey.get();
      if (pos > wordS.length - 1) {
        curWords = wordS[Random().nextInt(wordS.length)].toUpperCase().characters.toList();
      } else {
        curWords = wordS[pos].toUpperCase().characters.toList();
      }

      selectWords.clear();
      clearBack();
      selectIndex = 0;
      allowSelect = true;

      if (widget.wordN >= 3) {
        selectWords.add(curWords[selectIndex]);
        selectIndex++;
      }

      if (widget.wordN >= 5) {
        selectWords.add(curWords[selectIndex]);
        selectIndex++;
      }

      for (var i = selectIndex; i < curWords.length; i++) {
        banks.add(BackConfig(AnimationController(vsync: this, duration: const Duration(milliseconds: 250)), curWords[i]));
      }
      banks.shuffle();
    });
  }

  playAni(BackConfig config) {
    RenderBox tPos = config.key.currentContext?.findRenderObject() as RenderBox;
    RenderBox thPos = selectKey.currentContext?.findRenderObject() as RenderBox;

    var tLat = tPos.getTransformTo(null).getTranslation();
    var thTat = thPos.getTransformTo(null).getTranslation();

    //宽度加边距
    config.toX = thTat[0] + selectIndex * (68 + 8).w - tLat[0];
    config.toY = thTat[1] - tLat[1];

    var con = config.con;
    setAniEndLis(con, () {
      check(config);
    });
    con.forward(from: 0);
  }

  reverseAni(BackConfig config) {
    var con = config.con;
    setAniEndLis(con, () {
      allowSelect = true;
    });
    con.reverse(from: 1);
  }

  setAniEndLis(AnimationController con, Function() completed) {
    late AnimationStatusListener lis;
    lis = (status) {
      if (status == AnimationStatus.completed || status == AnimationStatus.dismissed) {
        con.removeStatusListener(lis);
        completed();
      }
    };
    con.addStatusListener(lis);
  }

  check(BackConfig config) async {
    setState(() {
      config.show = false;
      selectWords.add(config.wo);
    });

    if (selectWords.last == curWords[selectWords.length - 1]) {
      //填对
      if (selectWords.length == curWords.length) {
        correctNext();
      } else {
        selectIndex++;
        allowSelect = true;
      }
    } else {
      //填错
      await Future.delayed(const Duration(milliseconds: 600));
      if (!mounted) return;

      ability.add(-1);
      config.show = true;
      reverseAni(config);
      setState(() {
        selectWords.removeLast();
      });
    }
  }

  correctNext() async {
    setState(() => showHighlight = true);
    await Future.delayed(const Duration(milliseconds: 1500));
    if (!mounted) return;
    setState(() => showHighlight = false);
    await Future.delayed(const Duration(milliseconds: 200));
    if (!mounted) return;

    var ji = 10;
    var cPosMax5 = cPosMax5Key.get();
    cPosMax5--;
    if (cPosMax5 <= 0) {
      cPosMax5Key.put(cPosMax5Key.getDef());
      applevel.add(1);
      ji += 50;
    } else {
      cPosMax5Key.put(cPosMax5);
    }
    startPosKey.add(1);

    await CorrectShop(ji).shop(context);
    Jinb.putV(ji);
    update();
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
                  InkWell(
                    onTap: () {
                      Navigator.pop(context);
                    },
                    child: Image.asset(
                      iFile("oiirkre"),
                      width: 40.w,
                      height: 40.w,
                    ),
                  ),
                  SizedBox(width: 13.w),
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
              SizedBox(height: 80.w),
              Container(
                width: 420.w,
                height: 580.w,
                decoration: BoxDecoration(image: DecorationImage(image: AssetImage(iFile("griitr545")), fit: BoxFit.fill)),
                child: Column(
                  children: [
                    SizedBox(height: 23.w),
                    STextV(
                        text: wstrAssembly(wstr.les, applevelv.toString()),
                        strokeWidth: 1.w,
                        strokeColor: const Color(0xFF8B4923),
                        color: Colors.white,
                        fontSize: 28.sp),
                    SizedBox(height: 95.w),
                    Stack(
                      children: [
                        selectLayout(),
                        if (showHighlight)
                          Shimmer.fromColors(
                            period: const Duration(milliseconds: 750),
                            baseColor: Colors.transparent,
                            highlightColor: Colors.white.withOpacity(0.5),
                            child: Row(
                                mainAxisAlignment: MainAxisAlignment.center,
                                children: curWords
                                    .map((w) => Padding(
                                        padding: EdgeInsets.symmetric(horizontal: 4.w),
                                        child: Image.asset(
                                          iFile("ooriwj_CO"),
                                          width: 68.w,
                                          height: 68.w,
                                        )))
                                    .toList()),
                          ),
                      ],
                    ),
                    Expanded(
                      child: Padding(
                        padding: EdgeInsets.all(50.w),
                        child: bankLayout(),
                      ),
                    ),
                  ],
                ),
              ),
              SizedBox(height: 28.w),
              helpLayout()
            ],
          ),
        ),
      ),
    );
  }

  Widget selectLayout() {
    List<Widget> list = [];
    for (var i = 0; i < curWords.length; i++) {
      var key = i == 0 ? selectKey : null;
      if (selectWords.length > i) {
        list.add(Container(
          key: key,
          width: 68.w,
          height: 68.w,
          decoration: BoxDecoration(
              image: DecorationImage(
            image: AssetImage(iFile(selectWords[i] == curWords[i] ? "ooriwj_CO" : "ooriwj_FA")),
            fit: BoxFit.fill,
          )),
          child: Image.asset(
            iFile("ooriwj_${selectWords[i]}"),
            width: 68.w,
            height: 68.w,
          ),
        ));
      } else {
        list.add(Image.asset(
          key: key,
          iFile("ooriwj_NO"),
          width: 68.w,
          height: 68.w,
        ));
      }
    }
    return Row(
        mainAxisAlignment: MainAxisAlignment.center,
        children: list.map((w) => Padding(padding: EdgeInsets.symmetric(horizontal: 4.w), child: w)).toList());
  }

  Widget bankLayout() {
    return Row(
      mainAxisAlignment: MainAxisAlignment.spaceAround,
      crossAxisAlignment: CrossAxisAlignment.start,
      children: banks.map((b) {
        if (b.show) {
          return AnimatedBuilder(
              animation: b.con,
              builder: (context, child) => Transform.translate(
                    offset: Offset(b.con.value * b.toX, b.con.value * b.toY + b.randomY.w),
                    child: InkWell(
                      onTap: () {
                        if (allowSelect) {
                          if (ability.get() > 0) {
                            allowSelect = false;
                            if (showHelp && curWords[selectIndex] == b.wo) {
                              setState(() {
                                showHelp = false;
                              });
                            }
                            playAni(b);
                          } else {
                            const AbilityShop().shop(context);
                          }
                        }
                      },
                      child: Stack(
                        key: b.key,
                        alignment: Alignment.center,
                        children: [
                          Image.asset(
                            iFile("ooriwj_BAN"),
                            fit: BoxFit.fill,
                            width: 68.w,
                            height: 68.w,
                          ),
                          Transform.translate(
                            offset: Offset(0, -2.w),
                            child: Image.asset(
                              iFile("ooriwj_${b.wo}"),
                              width: 68.w,
                              height: 68.w,
                            ),
                          ),
                          if (showHelp && curWords[selectIndex] == b.wo)
                            IgnorePointer(
                              child: Transform.scale(
                                scale: 2,
                                child: Transform.translate(
                                  offset: Offset(6.w, 8.w),
                                  child: Lottie.asset(
                                    zFile("noirir"),
                                    renderCache: RenderCache.drawingCommands,
                                    width: 68.w,
                                    height: 68.w,
                                  ),
                                ),
                              ),
                            ),
                        ],
                      ),
                    ),
                  ));
        } else {
          return SizedBox(
            width: 68.w,
            height: 68.w,
          );
        }
      }).toList(),
    );
  }

  Widget helpLayout() {
    return Row(
      children: [
        SizedBox(width: 24.w),
        InkWell(
          onTap: () {
            if (allowSelect && !showHelp && ability.get() > 0) {
              if (helpKey.get() > 0) {
                helpKey.add(-1);
                setState(() {
                  showHelp = true;
                });
              } else {
                const HelpShop().shop(context);
              }
            }
          },
          child: Container(
            width: 120.w,
            height: 45.w,
            decoration: BoxDecoration(image: DecorationImage(image: AssetImage(iFile("ghrreww")), fit: BoxFit.fill)),
            child: Row(
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                Image.asset(
                  iFile("gefwq1"),
                  width: 32.w,
                  height: 32.w,
                ),
                SizedBox(width: 3.w),
                STextV(
                    text: "${helpKey.get()}/${helpKey.getDef()}",
                    strokeWidth: 1.w,
                    strokeColor: const Color(0xFF8B4923),
                    color: Colors.white,
                    fontSize: 17.sp)
              ],
            ),
          ),
        ),
        const Spacer(),
        InkWell(
          onTap: () {
            setState(() {
              for (var b in banks) {
                b.random();
              }
              banks.shuffle();
            });
          },
          child: Container(
            width: 120.w,
            height: 45.w,
            decoration: BoxDecoration(image: DecorationImage(image: AssetImage(iFile("ghrreww")), fit: BoxFit.fill)),
            child: Center(
              child: Image.asset(
                iFile("gefwq2"),
                width: 32.w,
                height: 32.w,
              ),
            ),
          ),
        ),
        SizedBox(width: 24.w),
      ],
    );
  }
}
