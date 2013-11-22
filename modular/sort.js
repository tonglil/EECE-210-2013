// Sorts the grades in descending order;
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
