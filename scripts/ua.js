/*
    Copyright (C) 2011-2020 Andy Cheung

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.
*/

const BROWSER_CHROME = 1;
const BROWSER_EDGE = 2;
const BROWSER_IE = 3;
const BROWSER_FIREFOX = 4;
const BROWSER_SAFARI = 5;
const BROWSER_UNKNOWN = -1;

const TERMINAL_WINDOWS = 1;
const TERMINAL_ANDROID = 2;
const TERMINAL_IPAD = 3;
const TERMINAL_WP = 4;
const TERMINAL_LINUX = 5;
const TERMINAL_IPHONE = 6;
const TERMINAL_UNKNOWN = -1;


function getBrowserType() {
    let ua = navigator.userAgent;

    if (ua.match(/Edge\/(\d.)+/) != null) {
        return BROWSER_EDGE;
    } else if (ua.match(/Chrome\/(\d.)+/) != null) {   // 在JS中，/.../ 代表正则表达式
        return BROWSER_CHROME;
    } else if (ua.match(/Trident\/(\d.)+/) != null) {
        return BROWSER_IE;
    } else if (ua.match(/Firefox\/(\d.)+/) != null) {
        return BROWSER_FIREFOX;
    } else if (ua.match(/Safari\/(\d.)+/)){
        return BROWSER_SAFARI;
    } else {
        return BROWSER_UNKNOWN;
    }
}

function getTerminalType() {
    let ua = navigator.userAgent;

    if (ua.match(/iPad;/) != null) {
        return TERMINAL_IPAD;
    } else if (ua.match(/iPhone;/) != null) {
        return TERMINAL_IPHONE;
    } else if (ua.match(/Android (\d.)+/) != null) {
        return TERMINAL_ANDROID;
    } else if (ua.match(/Windows/) != null) {
        return TERMINAL_WINDOWS;
    } else if (ua.match(/Linux/) != null){
        return TERMINAL_LINUX;
    } else if (ua.match(/Windows Phone (\d.)+/) != null) {
        return TERMINAL_WP;
    } else {
        return TERMINAL_UNKNOWN;
    }
}

function isMobileDevice()
{
    const type = getTerminalType();
    return type == TERMINAL_ANDROID || type == TERMINAL_WP || type == TERMINAL_IPAD || type == TERMINAL_IPHONE;
}