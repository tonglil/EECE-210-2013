// Sorts grades in descending order
// @par:    object
// @par:    string  grade column name (default 'grade')
// @par:    string  sorting order (default descending)
exports.sort = function(object, colName, order) {
    colName = (colName || 'grade');
    order = (order || null);

    var done = object.sort(function(a,b) {
        if (order === 'asc') {
            return a[colName] - b[colName];
        } else {
            return b[colName] - a[colName];
        }
    });

    return done;
}

// Helper function for cloning an object (until a constructor for grade data is built)
// http://stackoverflow.com/questions/728360/most-elegant-way-to-clone-a-javascript-object
// @par:    object  any object to be cloned
// @ret:    object  copy of @par
// @thr:    Error   unable to copy
exports.clone = function clone(obj) {
    // Handle the 3 simple types, and null or undefined
    if (null == obj || "object" != typeof obj) return obj;

    // Handle Date
    if (obj instanceof Date) {
        var copy = new Date();
        copy.setTime(obj.getTime());
        return copy;
    }

    // Handle Array
    if (obj instanceof Array) {
        var copy = [];
        for (var i = 0, len = obj.length; i < len; i++) {
            copy[i] = clone(obj[i]);
        }
        return copy;
    }

    // Handle Object
    if (obj instanceof Object) {
        var copy = {};
        for (var attr in obj) {
            if (obj.hasOwnProperty(attr)) copy[attr] = clone(obj[attr]);
        }
        return copy;
    }

    throw new Error("Unable to copy obj! Its type isn't supported.");
}
