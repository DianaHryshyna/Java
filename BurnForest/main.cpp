#include <iostream>
#include <chrono>
#include <thread>
#include <random>

using namespace std;

int main() {

    cout << "******************** BURN FOREST! *****************" << endl;
    cout << "Enter size of forest, size will be n*n :" << endl;

    int forestSize;
    cin >> forestSize;

    vector<vector<char>> forest;
    forest.resize(forestSize + 2);

    for(int i = 0; i < forestSize + 2; ++i) {
        forest[i].resize(forestSize + 2);
    }


    for(int i = 0; i < forestSize + 1; ++i) {
        for(int j = 0; j < forestSize + 2; ++j) {
            forest[i][j]='.';
        }
    }

    for(int i = 1; i < forestSize + 1; ++i) {
        for(int j = 1; j < forestSize + 1; ++j) {
            forest[i][j]='^';
        }
    }

    cout << "Do you want to enter the burn forest? (y-yes, n-no)" << endl;
    char answer;
    cin >> answer;

    while (answer != 'y' && answer != 'n') {
        cout << "Error! Try again!" << endl;
        cin >> answer;
        cout << '\n';
    }

    int x;
    int y;
    if(answer == 'y') {
        cout << "Enter position of the burn tree from 1 to size" << endl;

        cout << "X: ";
        cin >> x;

        cout << "Y: ";
        cin >> y;

        while(x <= 0 || x > forestSize || y <= 0 || y > forestSize){
            cout << "Error! Try again!" << endl;
            cout << "X: ";
            cin >> x;

            cout << "Y: ";
            cin >> y;
        }
    }

    system("CLS");

    int fireProbability;
    cout << "Enter probability of the fire(in %)" << endl;
    cin >> fireProbability;

    while(fireProbability < 0 || fireProbability > 100) {
        cout << "Try again, value must be from 0 to 100" << endl;
        cin >> fireProbability;
    }

    system("CLS");



    int rebirthProbability;
    cout << "Enter probability of the rebirth(in %)" << endl;
    cin >> rebirthProbability;

    while(rebirthProbability < 0 || rebirthProbability > 100) {
        cout << "Try again, value must be from 0 to 100" << endl;
        cin >> rebirthProbability;
    }

    system("CLS");



    int selfIgnitionProbability;
    cout << "Enter probability of the self ignition(in %)" << endl;
    cin >> selfIgnitionProbability;

    while(selfIgnitionProbability < 0 || selfIgnitionProbability > 100) {
        cout << "Try again, value must be from 0 to 100" << endl;
        cin >> selfIgnitionProbability;
    }

    system("CLS");


    int generation;
    cout << "Enter amount of the generation(in %)" << endl;
    cin >> generation;
    system("CLS");


    unsigned seed=chrono::system_clock::now().time_since_epoch().count();
    minstd_rand0 generator(seed);

    vector<vector<char>> forestCopy;
    forestCopy.resize(forestSize + 2);

    for(int i = 0; i < forestSize + 2; ++i) {
        forestCopy[i].resize(forestSize + 2);
    }

    for(int i = 0; i < generation; ++i) {

        for(int j = 0; j < forestSize + 2; ++j) {
            for(int k = 0; k < forestSize + 2; ++k) {
                forestCopy[j][k]=forest[j][k];
            }
        }

        for(int j = 1; j < forestSize + 1; ++j) {
            for(int k = 1; k < forestSize + 1; ++k)  {

                cout << forestCopy[j][k];

                switch(forestCopy[j][k])
                {
                    case '*':
                    {
                        forest[j][k] = '.';
                        break;
                    }
                    case '.':
                    {
                        if(forestCopy[j-1][k-1] == '*' || forestCopy[j-1][k] == '*' || forestCopy[j-1][k+1] == '*') {
                            break;
                        }
                        else if(forestCopy[j][k-1] == '*'|| forestCopy[j][k+1] == '*') {
                            break;
                        }
                        else if(forestCopy[j+1][k-1] == '*' || forestCopy[j+1][k] == '*' || forestCopy[j+1][k+1] == '*') {
                            break;
                        }
                        else {
                            if(generator()%101 < rebirthProbability) {
                                forest[j][k] = '^';
                            }
                        }
                        break;
                    }
                    case '^':
                    {
                        if(forestCopy[j-1][k-1] == '*' || forestCopy[j-1][k] == '*' || forestCopy[j-1][k+1]== '*') {
                            if(generator()%101 < fireProbability) {
                                forest[j][k] = '*';
                            }
                        }
                        else if(forestCopy[j][k-1] == '*'|| forestCopy[j][k+1] == '*') {
                            if(generator()%101 < fireProbability) {
                                forest[j][k] = '*';
                            }
                        }
                        else if(forestCopy[j+1][k-1] == '*' || forestCopy[j+1][k] == '*' || forestCopy[j+1][k+1] == '*') {
                            if(generator()%101<fireProbability) {
                                forest[j][k]='*';
                            }
                        }
                        else {
                            if(generator()%101 < selfIgnitionProbability) {
                                forest[j][k] = '*';
                            }
                        }
                        break;
                    }
                    default:
                        break;
                }
            }
            cout<<"\n";
        }

        cout << "Generation" << " " << i << endl;
        this_thread::sleep_for(chrono::seconds(2));
        system("CLS");
    }

    return 0;
}
