testCases = 1

def getTestCases():
    global T, f
    # T = int(input())
    T = int(f.readline())

def getMatrixDimAndModelNumbers():
    global N, M, grid, placedLocations, score, freeRows, freeCols, freeDiag, numberOfChanges, listChanges
    freeRows = []
    freeCols = []
    freeDiag = []
    placedLocations = []
    listChanges = []
    score = 0
    numberOfChanges = 0
    # N, M = [int(x) for x in input().split()]
    N, M = [int(x) for x in f.readline().split()]
    grid = ["."] * N
    for i in range(N):
        grid[i] = ["."] * N
    for i in range(0,N):
        freeRows.append(i)
        freeCols.append(i)
    for i in range(N):
        for j in reversed(range(N)):
            freeDiag.append([j,i])

def displaygrid():
    global N, grid
    for i in range(N):
        print(grid[i])

def insertModelsPositions():
    global M, model, R, C, grid, placedLocations
    i = 0
    while i != M:
        # model, R, C = list(input().split())
        model, R, C = list(f.readline().split())
        R = int(R) - 1
        C = int(C) - 1
        if model in ["x"]:
            grid[R][C] = model
            freeRows.remove(R)
            freeCols.remove(C)
        elif model in ["+"]:
            grid[R][C] = model
            freeDiag.remove([R,C])
        elif model in ["o"]:
            grid[R][C] = model
            freeRows.remove(R)
            freeCols.remove(C)
            freeDiag.remove([R,C])
        i += 1

def addXtoMatrix():
    global R, C, grid, freeRows, freeCols, placedLocations, N, numberOfChanges, listChanges
    for R, C in zip(freeRows,freeCols):
        if grid[R][C] == "+":
            grid[R][C] = "o"
            if ["+", R+1, C+1] in listChanges:
                listChanges.remove(["+", R+1, C+1])
                numberOfChanges -= 1
            if [R,C] not in placedLocations:
                placedLocations.append([R,C])
            numberOfChanges += 1
            listChanges.append(["o", R+1, C+1])
        elif grid[R][C] == ".":
            grid[R][C] = "x"
            placedLocations.append([R,C])
            numberOfChanges += 1
            listChanges.append(["x", R+1, C+1])
                

def addPlustoMatrix():
    global R, C, grid, N, placedLocations, freeDiag, numberOfChanges
    for R, C in freeDiag:
        cond1 = True
        cond2 = True
        try:
        # Diagonal creciente de izquierda a derecha
            for k in range(1, N):
                i = R - k
                j = C + k
                if 0 <= i <= (N-1) and 0 <= j <= (N-1):
                    if grid[i][j] != "+" and grid[i][j] != "o":
                        cond1 = True
                    else:
                        cond1 = False
                        break
            #Diagonal creciente de derecha a izquierda
            for k in range(1, N):
                i = R - k
                j = C - k
                if 0 <= i <= (N-1) and 0 <= j <= (N-1):
                    if grid[i][j] != "+" and grid[i][j] != "o":
                        cond2 = True
                    else:
                        cond2 = False
                        break
            if cond1 and cond2:
                if grid[R][C] == "x":
                    grid[R][C] = "o"
                    placedLocations.append([R,C])
                    numberOfChanges += 1
                    listChanges.append(["o", R+1, C+1])
                    freeDiag.remove([C,R])
                elif grid[R][C] == ".":
                    grid[R][C] = "+"
                    placedLocations.append([R,C])
                    numberOfChanges += 1
                    listChanges.append(["+", R+1, C+1])
                    freeDiag.remove([C,R])
        except UnboundLocalError:
                grid[R][C] = "+"
                placedLocations.append([R,C])
                numberOfChanges += 1
                listChanges.append(["+", R+1, C+1])

def sumPoints():
    global grid, N, score
    for R in range(N):
        for C in range(N):
            if grid[R][C] == "x" or grid[R][C] == "+":
                score = score + 1
            elif grid[R][C] == "o":
                score = score + 2

def printResults():
    global testCases, score, numberOfChanges, placedLocations, res
    res = open("output_file.in","a")
    res.write("Case #{}: {} {}\n".format(testCases, score, numberOfChanges))
    placedLocations = sorted(placedLocations , key=lambda k: [k[0], k[1]])
    for R, C in placedLocations:
        res.write("{} {} {}\n".format(grid[R][C],R+1,C+1))

f = open("D-small-practice.in","r")
res = open("output_file.in","w")
getTestCases()
while testCases != T+1:
    getMatrixDimAndModelNumbers()
    insertModelsPositions()
    addPlustoMatrix()
    addXtoMatrix()
    sumPoints()
    printResults()
    testCases += 1
f.close()
res.close()