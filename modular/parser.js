// Clean the array to id & grades only
// @par:    array   parsed 2D array
// @par:    string  grade column name (default 'grade')
// @ret:    object
exports.cleanData = function(parsed, colName) {
    colName = (colName || 'grade');

    var cleaned = new Array();

    for (var i in parsed) {
        if (isNaN(parseFloat(parsed[i][0]))) {
            delete parsed[i];
        }
    }

    parsed = parsed.filter(function() { return true; });

    for (var i in parsed) {
        for (var j = parsed[0].length + 2; j >= 0; j--) {
            var num = parseFloat(parsed[i][j]);
            if (isNaN(num)) {
                delete parsed[i][j];
                parsed[i] = parsed[i].filter(function() { return true; });
            } else {
                parsed[i][j] = num;
            }
        }

        var obj = {};
        var width = parsed[0].length - 1;
        obj['id'] = parsed[i][0];
        obj[colName] = parsed[i][width];
        cleaned.push(obj);
    }

    return cleaned;
}

// Parse a delimited string into an array of arrays.
// http://www.bennadel.com/blog/1504-Ask-Ben-Parsing-CSV-Strings-With-Javascript-Exec-Regular-Expression-Command.htm
// @par:    string  delimited string
// @par:    string  delimiter (default ',')
// @ret:    array
exports.csvToArray = function(strData, strDelimiter) {
    strDelimiter = (strDelimiter || ",");

    // Create a regular expression to parse the CSV values
    var objPattern = new RegExp(
        (
            // Delimiters
            "(\\" + strDelimiter + "|\\r?\\n|\\r|^)" +

            // Quoted fields
            "(?:\"([^\"]*(?:\"\"[^\"]*)*)\"|" +

            // Standard fields
            "([^\"\\" + strDelimiter + "\\r\\n]*))"
    ),
    "gi"
    );

    // Array to hold data, default empty first row
    var arrData = [[]];

    // Array to hold individual pattern matching groups
    var arrMatches = null;

    // Keep looping over the regular expression matches until no matches
    while (arrMatches = objPattern.exec(strData)) {
        // Get the delimiter that was found.
        var strMatchedDelimiter = arrMatches[1];

        // Check to see if the given delimiter has a length
        // (is not the start of string) and if it matches
        // field delimiter. If id does not, then we know
        // that this delimiter is a row delimiter.
        if (strMatchedDelimiter.length && (strMatchedDelimiter != strDelimiter)) {
            // Since we have reached a new row of data,
            // add an empty row to our data array.
            arrData.push([]);
        }

        // Now that we have our delimiter out of the way,
        // let's check to see which kind of value we
        // captured (quoted or unquoted).
        if (arrMatches[2]) {
            // We found a quoted value. When we capture
            // this value, unescape any double quotes.
            var strMatchedValue = arrMatches[2].replace(
                new RegExp( "\"\"", "g" ),
                "\""
            );
        } else {
            // We found a non-quoted value.
            var strMatchedValue = arrMatches[3];
        }

        // Now that we have our value string, let's add
        // it to the data array.
        arrData[arrData.length - 1].push(strMatchedValue);
    }

    // Return the parsed data.
    return(arrData);
}
