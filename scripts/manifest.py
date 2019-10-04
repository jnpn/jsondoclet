#!/usr/bin/env python

'''
generates a Class-Path: <lib>... to save in a jar manifest
'''

import packages as p

def manifest():
    libs = p.libs(['--libs', 'libs'], sep=' ')
    return f'Class-Path: {libs}'

FN='meta-inf/manifest-cp.mf'

def main():
    with open(FN,'w') as mf:
        mf.write(manifest())

if __name__ == '__main__':
    main()
