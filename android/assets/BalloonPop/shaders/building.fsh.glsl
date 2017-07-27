
#extension GL_OES_standard_derivatives : enable
#ifdef GL_ES
precision mediump float;
#endif


#define PI 3.1415926535
uniform float time;
uniform vec2 mouse;
uniform vec2 resolution;
// 	<www.shadertoy.com/view/XsX3zB>
//	by Nikita Miropolskiy

/* discontinuous pseudorandom uniformly distributed in [-0.5, +0.5]^3 */
vec3 random3(vec3 c) {
	float j = 4096.0*sin(dot(c,vec3(17.0, 59.4, 15.0)));
	vec3 r;
	r.z = fract(512.0*j);
	j *= .125;
	r.x = fract(512.0*j);
	j *= .125;
	r.y = fract(512.0*j);
	return r-0.5;
}

const float F3 =  0.3333333;
const float G3 =  0.1666667;
float snoise(vec3 p) {

	vec3 s = floor(p + dot(p, vec3(F3)));
	vec3 x = p - s + dot(s, vec3(G3));

	vec3 e = step(vec3(0.0), x - x.yzx);
	vec3 i1 = e*(1.0 - e.zxy);
	vec3 i2 = 1.0 - e.zxy*(1.0 - e);

	vec3 x1 = x - i1 + G3;
	vec3 x2 = x - i2 + 2.0*G3;
	vec3 x3 = x - 1.0 + 3.0*G3;

	vec4 w, d;

	w.x = dot(x, x);
	w.y = dot(x1, x1);
	w.z = dot(x2, x2);
	w.w = dot(x3, x3);

	w = max(0.6 - w, 0.0);

	d.x = dot(random3(s), x);
	d.y = dot(random3(s + i1), x1);
	d.z = dot(random3(s + i2), x2);
	d.w = dot(random3(s + 1.0), x3);

	w *= w;
	w *= w;
	d *= w;

	return dot(d, vec4(52.0));
}

float snoiseFractal(vec3 m) {
	return   0.5333333* snoise(m)
				+0.2666667* snoise(2.0*m)
				+0.1333333* snoise(4.0*m)
				+0.0666667* snoise(8.0*m);
}


vec2 fold(vec2 pos, float b)
{
	pos += 0.4;
	for (float i = 0.0; i < 3.0; ++i)
	{
		float a = PI / 4.0 - 0.4 + sin(time * 0.1 + i * 2.5 + b) * 0.4;
    		pos = abs(pos);
    		pos *= mat2(cos(a), -sin(a), sin(a), cos(a));
    		pos -= 0.8  * pow(2.0, -i);
    		pos = abs(pos);
		if (pos.x < pos.y) pos.xy = pos.yx;
	}
	return pos;
}

vec3 background(vec2 pos)
{
	float fp = floor(pos.x * 20.0);

	if (pos.y > snoiseFractal(vec3(time * 0.05, 0.1, fp * 100.0)) * 0.3 + 0.4)
	{
		return vec3(1, 1, 1);
	}

	vec2 pos2 = pos;
	pos2.y += fp;

	float noise = 0.0;
	noise += snoiseFractal(vec3(pos2 * 500.0, time * 1.3)) * (snoiseFractal(vec3(pos2 * 1.0, time * 0.1)) + 0.2);

	noise += snoiseFractal(vec3(pos2 * 0.5, time * 0.3)) * 0.8;
	noise += 0.3;
	pos = fold(pos, 2.5);
	pos.y += sin(fp) * 0.1;
	noise += pos.x < pos.y ? 0.0 : 0.2;

	return mix(vec3(0.5, 0.95, 1), vec3(0.1, 0.5, 1.0), noise);
}

void main( void ) {

	vec2 pos = (gl_FragCoord.xy - resolution.xy / 2.0) / resolution.y;

	vec3 color;
	color = background(pos);
	pos += 0.4;
	vec2 posA = fold(pos, 0.0);
	vec2 posB = fold(pos, 1.0);

	color += 0.0005 / abs(posA.y);
    	color += 0.004 / length(posA);

	color = mix(vec3(1, 0.3, 0.8), color, clamp(abs(posB.y - posB.x) / 0.005, 0.0, 1.0));


	gl_FragColor = vec4(color, 1);

}