
#ifdef GL_OES_standard_derivatives
    #extension GL_OES_standard_derivatives : enable
#endif

#ifdef GL_ES
precision mediump float;
#endif


uniform float time;
uniform vec2 resolution;

const vec3 totalRayleigh = vec3(0.3, 0.5, 1.0);

vec3 getSkyAbsoption(vec3 x, float y){

	vec3 absoption = totalRayleigh * y;
	     absoption = pow(absoption, vec3(1.0 - y)) / x / y;

	return absoption;
}

float getSunPoint(vec2 p, vec2 lp){
	return smoothstep(0.03, 0.026, distance(p, lp)) * 50.0;
}

float getRayleigMultiplier(vec2 p, vec2 lp, float y){
	float disk = max(1.0 - distance(p, lp), 0.0);

	return disk*disk*(3.0 - 2.0 * disk);
}

float getMie(vec2 p, vec2 lp){
	float disk = clamp(1.0 - pow(distance(p, lp), 0.1), 0.0, 1.0);
	      disk *= disk*(3.0 - 2.0 * disk);

	return disk*disk * 32.0;
}

vec3 getAtmosphericScattering(vec2 p, vec2 lp){
	vec2 correctedLp = lp / max(resolution.x, resolution.y) * resolution;

	float zenith = 1.0  / sqrt(p.y) - 0.7;
	float sunPointDistMult = clamp(distance(lp.y, 0.0), 0.0, 1.0);

	float rayleighMult = getRayleigMultiplier(p, correctedLp, zenith);

	vec3 absorption = getSkyAbsoption(totalRayleigh, zenith);
	vec3 sky = totalRayleigh * zenith * (1.0 + rayleighMult);
	vec3 sun = getSunPoint(p, correctedLp) * absorption;
	vec3 mie = getMie(p, correctedLp) * absorption * zenith;

	vec3 totalSky = mix(sky * absorption, sky / (sky + 0.5), sunPointDistMult);

        totalSky += sun + mie;

	totalSky *= smoothstep(0.0, 0.1, sunPointDistMult);

	return totalSky;
}

void main( void ) {
	float sunY=0.4*sin(time*0.4);
	if(sunY<0.0)
		sunY=0.0;
	vec2 mo = vec2(0.5+0.4*cos(time*0.4),sunY);
	vec2 position = gl_FragCoord.xy / max(resolution.x, resolution.y) * 2.0;
	vec2 lightPosition = mo * 2.0;

	vec3 color = getAtmosphericScattering(position, lightPosition) * 4.0;
	color /= color + 1.0;

	gl_FragColor = vec4(color*color, 1.0 );

}