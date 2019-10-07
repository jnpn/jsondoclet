#!/usr/bin/env python

'''
lists files to constitute a class-path (run time) or a source-set (compile time)
'''

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
def sourceset(cli):
    return sources(ROOT) + ' ' + sources(ROOT, pattern='*.java')

def zipmap(a,b,f):
    return [f(i,j) for i,j in zip(a,b)]

def match(a, b, strict=False):
    return all(zipmap(a,b,lambda u,v: u == v))

def vmatch(a, b, var=None):
    return all(zipmap(a,b,lambda u,v: u is var or v is var or u == v))

def libs(cli, sep=':'):
    flag, root, *_ = cli
    dbg(flag, root, _)
    toplevel = sources(root, sep=sep, pattern='**/*.jar')
    deeplevel = sources(root, sep=sep, pattern='**/**/*.jar')
    return sep.join([toplevel, deeplevel])


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
            print(sourceset(cli))
        elif vmatch(cli, ['--libs', None]):
            print(libs(cli))
        else:
            print('wat')
    else:
        usage()
