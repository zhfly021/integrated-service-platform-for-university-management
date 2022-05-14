layui.use(['form', 'layer', 'jquery'], function() {
	var form = layui.form,
		layer = parent.layer === undefined ? layui.layer : top.layer
	$ = layui.jquery;



	// //登录按钮
	// form.on("submit(login)", function(data) {
	// 	$(this).text("登录中...").attr("disabled", "disabled").addClass("layui-disabled");
	// 	// setTimeout(function() {
	// 	// 	var index = layer.msg('登录中，请稍候', {
	// 	// 		icon: 16,
	// 	// 		time: false,
	// 	// 		shade: 0.8
	// 	// 	});
	// 	// 	// window.location.href = "../index.html";
	// 	// 	$.ajax({
	// 	// 		"url":"/admin/login",
	// 	// 		// "data": userName : $("#name").val()
	// 	// 		"type":"post",
	// 	// 		"dataType":"text",
	// 	// 		// "success":callback
	// 	// 	});
	// 	// }, 1000);
	// 	// return false;
	// })

	//表单输入效果
	$(".loginBody .input-item").click(function(e) {
		e.stopPropagation();
		$(this).addClass("layui-input-focus").find(".layui-input").focus();
	})
	$(".loginBody .layui-form-item .layui-input").focus(function() {
		$(this).parent().addClass("layui-input-focus");
	})
	$(".loginBody .layui-form-item .layui-input").blur(function() {
		$(this).parent().removeClass("layui-input-focus");
		if ($(this).val() != '') {
			$(this).parent().addClass("layui-input-active");
		} else {
			$(this).parent().removeClass("layui-input-active");
		}
	})
});


		
//宇宙特效
"use strict";
var canvas = document.getElementById('canvas'),
	ctx = canvas.getContext('2d'),
	w = canvas.width = window.innerWidth,
	h = canvas.height = window.innerHeight,
	hue = 217,
	stars = [],
	count = 0,
	maxStars = 2500; //星星数量

var canvas2 = document.createElement('canvas'),
	ctx2 = canvas2.getContext('2d');
canvas2.width = 100;
canvas2.height = 100;
var half = canvas2.width / 2,
	gradient2 = ctx2.createRadialGradient(half, half, 0, half, half, half);
gradient2.addColorStop(0.025, '#CCC');
gradient2.addColorStop(0.1, 'hsl(' + hue + ', 61%, 33%)');
gradient2.addColorStop(0.25, 'hsl(' + hue + ', 64%, 6%)');
gradient2.addColorStop(1, 'transparent');

ctx2.fillStyle = gradient2;
ctx2.beginPath();
ctx2.arc(half, half, half, 0, Math.PI * 2);
ctx2.fill();

function random(min, max) {
	if (arguments.length < 2) {
		max = min;
		min = 0;
	}
	if (min > max) {
		var hold = max;
		max = min;
		min = hold;
	}

	return Math.floor(Math.random() * (max - min + 1)) + min;
}

function maxOrbit(x, y) {
	var max = Math.max(x, y),
		diameter = Math.round(Math.sqrt(max * max + max * max));
	return diameter / 2;
	//星星移动范围，值越大范围越小，
}

var Star = function() {

	this.orbitRadius = random(maxOrbit(w, h));
	this.radius = random(60, this.orbitRadius) / 18;
	//星星大小
	this.orbitX = w / 2;
	this.orbitY = h / 2;
	this.timePassed = random(0, maxStars);
	this.speed = random(this.orbitRadius) / 500000;
	//星星移动速度
	this.alpha = random(2, 10) / 10;

	count++;
	stars[count] = this;
}

Star.prototype.draw = function() {
	var x = Math.sin(this.timePassed) * this.orbitRadius + this.orbitX,
		y = Math.cos(this.timePassed) * this.orbitRadius + this.orbitY,
		twinkle = random(10);

	if (twinkle === 1 && this.alpha > 0) {
		this.alpha -= 0.05;
	} else if (twinkle === 2 && this.alpha < 1) {
		this.alpha += 0.05;
	}

	ctx.globalAlpha = this.alpha;
	ctx.drawImage(canvas2, x - this.radius / 2, y - this.radius / 2, this.radius, this.radius);
	this.timePassed += this.speed;
}

for (var i = 0; i < maxStars; i++) {
	new Star();
}

function animation() {
	ctx.globalCompositeOperation = 'source-over';
	ctx.globalAlpha = 0.5; //尾巴
	ctx.fillStyle = 'hsla(' + hue + ', 64%, 6%, 2)';
	ctx.fillRect(0, 0, w, h)

	ctx.globalCompositeOperation = 'lighter';
	for (var i = 1, l = stars.length; i < l; i++) {
		stars[i].draw();
	};

	window.requestAnimationFrame(animation);
}

animation();
