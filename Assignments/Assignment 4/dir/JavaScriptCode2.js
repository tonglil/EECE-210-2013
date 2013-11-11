function foo1(x,y) {
    a = x;
    if (a != y) {
        b = x;
        a = y
    }
    if (b == x - y || a == x - y) {
        c = x - y;
    }
}

function foo2(z) {
    a = 1;
    b = 2;
    c = 3;
    d = 4;
}

function foo3(m,n,o) {
    if (m == n && n > 9) {
        a = m + n + o;
        b = m - n - (a - o);
        if (b != 0) {
            b = 0;
            a = 1;
            if (a != b && a < b) {
                c = a + b;
            }
        }
    }
}