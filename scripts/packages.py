#!/usr/bin/env python

# import os

# def packages(root,psep=' ', ext='*.java'):
#     sep = os.path.sep
#     return psep.join(os.path.sep.join([top, d, ext]) for top,ds,fs in os.walk(root) for d in ds)

import os
import sys
import glob

DEBUG = False

def dbg(*a, **k):
    if DEBUG:
        print('[debug]', end=' ')
        print(*a)

def sources(root, sep=' ', pattern='**/*.java'):
    matches = glob.glob(root + pattern)
    return sep.join(matches)

ROOT='src/jnpn/json/'
def main():
    print(sources(ROOT), sources(ROOT, pattern='*.java'))

def zipmap(a,b,f):
    return [f(i,j) for i,j in zip(a,b)]

def match(a, b, strict=False):
    return all(zipmap(a,b,lambda u,v: u == v))

def vmatch(a, b, var=None):
    return all(zipmap(a,b,lambda u,v: u is var or v is var or u == v))

def usage():
    print(__file__, ':')
    print()
    print(__file__, '--source', '<dir>')
    print(__file__, '--libs', '<dir>')

if __name__ == '__main__':
    dbg(os.getcwd())
    this, *cli = sys.argv
    if cli:
        if vmatch(cli, ['--sources', None]):
            main()
        elif vmatch(cli, ['--libs', None]):
            flag, root, *_ = cli
            dbg(flag, root, _)
            sep = ':'
            toplevel = sources(root, sep=sep, pattern='**/*.jar')
            deeplevel = sources(root, sep=sep, pattern='**/**/*.jar')
            print(sep.join([toplevel, deeplevel]))
        else:
            print('wat')
    else:
        usage()
