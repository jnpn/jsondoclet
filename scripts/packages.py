#!/usr/bin/env python

# import os

# def packages(root,psep=' ', ext='*.java'):
#     sep = os.path.sep
#     return psep.join(os.path.sep.join([top, d, ext]) for top,ds,fs in os.walk(root) for d in ds)

import glob

def sources(root, sep=' ', pattern='**/*.java'):
    matches = glob.glob(root + pattern)
    return sep.join(matches)

ROOT='src/jnpn/json/'
def main():
    print(sources(ROOT), sources(ROOT, pattern='*.java'))

if __name__ == '__main__':
    main()
