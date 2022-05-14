// var notifyTipTimer = setInterval(function(){
//   var $headFixed = document.querySelector('.head_fixed');
//   if(document.querySelector('#resume_pc')){
//     clearInterval(notifyTipTimer);
//   }else if($headFixed){
//     clearInterval(notifyTipTimer);
//     var $headFixedFirst= $headFixed.childNodes[0];
//     var $notifyTip = document.createElement("div");
//     $notifyTip.className='notify_tip';
//     $notifyTip.innerHTML = '<p class="page_w"><b>公告：</b>本站将于2020年12月09日 19:30~19:35 进行升级维护（优化功能和修复已知bug），期间可能会造成短暂卡顿，给各位带来不便敬请谅解！<p>';
//     $headFixed.insertBefore($notifyTip,$headFixedFirst);
//   }
// },500);